package com.example.retrofit.seriesdetail.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Seasons(
    val id: Int?,
    val number: Int?,
    val summary: String?,
    val episodeOrder: Int?,
    val image: Image?
) : Parcelable
@Parcelize
data class Image(
    val medium: String?,
    val original: String?
    ): Parcelable
