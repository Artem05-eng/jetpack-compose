package com.example.futurama.ui

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.futurama.classes.Character
import com.example.futurama.R
import com.google.gson.Gson


@Composable
fun CharacterView(character: Character, navController: NavController) {
    Row(
        modifier = Modifier.clickable(
            onClick = {
                navController.currentBackStackEntry?.arguments = Bundle().apply { putParcelable("key", character) }
                navController.navigate("detailView")
            }
        ).padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideWithImage(data = character.image, modifier = Modifier.size(120.dp))
        Column(
            modifier = Modifier.padding(start = 6.dp)
        ) {
            Text(
                text = character.name,
                maxLines = 1,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = character.status + "-" + character.species,
                maxLines = 1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Last known location:",
                maxLines = 1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = character.location.name,
                maxLines = 1,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}



@Composable
fun GlideWithImage(
    data: Any?,
    modifier: Modifier? = null,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    if (data == null) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = contentDescription,
            modifier = modifier ?: Modifier,
            alignment = Alignment.Center,
            contentScale = contentScale
        )
    } else {
        Image(
            painter = rememberImagePainter(
                data = data,
                builder = {
                    placeholder(R.drawable.ic_launcher_background)
                }),
            contentDescription = contentDescription,
            modifier = modifier ?: Modifier
        )
    }
}

@Composable
fun GetView(character: Character) {
    Column(
        modifier = Modifier.padding(6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        GlideWithImage(data = character.image, modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(4.dp)), contentScale = ContentScale.Crop)
        Text(
            text = character.name,
            maxLines = 1,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = "Live status",
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = character.status,
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = "Species and gender:",
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = character.species + "-" + character.gender,
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = "Lat known location:",
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = character.location.name,
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = "First seen in:",
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = character.created,
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(character.episode) { item ->
                getCardEpisode(item)
            }
        }
    }
}

@Composable
fun getCardEpisode(item: String) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = item,
            maxLines = 2,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
