package com.example.futurama

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.futurama.classes.Character
import com.example.futurama.ui.CharacterView
import com.example.futurama.ui.GetView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.coroutineContext


class FuturamaFragment: Fragment() {

    private val viewModel by viewModels<ListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T = ListViewModel() as T
        }
    }
    private val pageData by lazy { ListPagedSource.pager(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "listCharacterView") {
                composable("listCharacterView") { ListCharacterView(navController) }
                composable("detailView") { DetailView(navController) }
            }
        }
    }

    @Composable
    private fun ListCharacterView(navController: NavController) {
        val charcterItems: LazyPagingItems<Character> =
            pageData.flow.cachedIn(CoroutineScope(Dispatchers.IO)).collectAsLazyPagingItems()

        LazyColumn {
            items(charcterItems) {
                it?.let {
                    CharacterView(
                        character = it,
                        navController = navController
                    )
                        ?: Text(text = "Ooops")
                }
                charcterItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            this@LazyColumn.item {
                                Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        loadState.append is LoadState.Loading -> {
                            this@LazyColumn.item { CircularProgressIndicator() }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = loadState.refresh as LoadState.Error
                            this@LazyColumn.item {
                                Column(
                                    modifier = Modifier.fillParentMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    e.error.localizedMessage?.let { Text(text = it) }
                                    Button(onClick = { retry() }) {
                                        Text(text = "Retry")
                                    }
                                }
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = loadState.refresh as LoadState.Error
                            this@LazyColumn.item {
                                Column(
                                    modifier = Modifier.fillParentMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    e.error.localizedMessage?.let { Text(text = it) }
                                    Button(onClick = { retry() }) {
                                        Text(text = "Retry")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun DetailView(navController: NavController) {
        val args = navController.previousBackStackEntry?.arguments?.getParcelable<Character>("key")
    if (args == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = args?.name ?: "Ooops",
                maxLines = 1,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold) 
            }
        } else {
            GetView(character = args)
        }
    }
}