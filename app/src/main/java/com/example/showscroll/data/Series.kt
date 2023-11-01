package com.example.showscroll.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Series(
    val genres: List<String>,
    val id: Int,
    val image: Image?,
    val name: String,
    val summary: String?,
    val type: String,
) : Parcelable
@Parcelize
data class Image(
    val medium: String,
    val original: String?
) : Parcelable