package com.example.futurama.classes

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
//@JsonClass(generateAdapter = true)
data class Character (
    val id: Int,
    val name: String,
    val type: String,
    val species: String,
    val status: String,
    val gender: String,
    val image: String,
    val location: Location,
    val created: String,
    val episode: List<String>
): Parcelable

//@JsonClass(generateAdapter = true)
data class Info(
    val pages: Int
)

//@JsonClass(generateAdapter = true)
data class CharacterWrapper<T>(
    val info: Info,
    val results: List<T>
)

@Parcelize
//@JsonClass(generateAdapter = true)
data class Location(
    val name: String,
    val url: String
): Parcelable

@Parcelize
//@JsonClass(generateAdapter = true)
data class Loc(
    val id: Int,
    val name: String,
    val type: String,
    val created: String
): Parcelable