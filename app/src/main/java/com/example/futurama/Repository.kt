package com.example.futurama

import com.example.futurama.classes.Character
import com.example.futurama.classes.Loc
import com.example.futurama.classes.Location
import com.example.futurama.networks.Networking
import android.util.Log
import com.example.futurama.classes.CharacterWrapper
import com.google.accompanist.imageloading.rememberDrawablePainter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Repository {

    fun getCharcterList(page: Int): List<Character> {
        val response = Networking.futuramaApi.getCharacters(page).execute()
        if (response.isSuccessful) {
            return response!!.body()!!.results ?: emptyList()
        } else {
            return emptyList()
        }
    }

    fun getSingleCharacter(id: Int): Character? {
        val response = Networking.futuramaApi.getSingleCharacter(id).execute()
        if (response.isSuccessful) {
            return response!!.body()!!
        } else {
            return null
        }
    }
}