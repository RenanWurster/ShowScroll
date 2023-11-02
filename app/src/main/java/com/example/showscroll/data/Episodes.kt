package com.example.showscroll.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Episodes(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val image: ImageEpisodes?,
    val summary: String?,
    val runtime: Int,
) : Parcelable
@Parcelize
data class ImageEpisodes(
    val medium: String,
    val original: String?
): Parcelable