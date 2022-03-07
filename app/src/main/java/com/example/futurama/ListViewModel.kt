package com.example.futurama

import androidx.lifecycle.ViewModel
import com.example.futurama.classes.Character
import com.example.futurama.classes.Loc
import com.example.futurama.classes.Location
import kotlinx.coroutines.delay
import com.example.futurama.networks.Networking
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class ListViewModel : ViewModel() {
    var newPage: Int? = null

    private val repository = Repository()

    suspend fun loadCharacter(page: Int): List<Character> {
        newPage = page
        val list = repository.getCharcterList(page)
        return list
    }
}