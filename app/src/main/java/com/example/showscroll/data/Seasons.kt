package com.example.showscroll.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Seasons(
    val id: Int?,
    val number: Int?,
    val summary: String?,
    val episodeOrder: Int?,
    val image: ImageSeasons?
) : Parcelable
@Parcelize
data class ImageSeasons(
    val medium: String?,
    val original: String?
    ): Parcelable
