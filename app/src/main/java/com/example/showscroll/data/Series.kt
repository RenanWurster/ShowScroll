package com.example.showscroll.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Series(
    val genres: List<String>,
    val id: Int,
    val image: ImageSerie?,
    val name: String,
    val summary: String?,
    val premiered: String?,
    val ended: String?,
    val type: String,
) : Parcelable
@Parcelize
data class ImageSerie(
    val medium: String,
    val original: String?
) : Parcelable