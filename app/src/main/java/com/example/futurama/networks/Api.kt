package com.example.futurama.networks

import com.example.futurama.classes.Character
import com.example.futurama.classes.CharacterWrapper
import com.example.futurama.classes.Loc
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("/api/character/")
    fun getCharacters(@Query("page") page: Int): Call<CharacterWrapper<Character>>

    @GET("/api/location")
    fun getLocations(@Query("page") page: Int): Call<CharacterWrapper<Loc>>

    @GET("/api/character/{id}")
    fun getSingleCharacter(@Path("id") id: Int): Call<Character>
}