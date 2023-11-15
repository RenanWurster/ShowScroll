package com.example.showscroll.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
  val id: Int,
  val url: String,
  val name: String,
  val country: Country,
  val birthday: String?,
  val deathday: String?,
  val gender: String,
  val image: Image?,
  val updated: Long,
  val _links: Links
) : Parcelable

@Parcelize
data class Character(
  val person: Person,
  val character: CharacterInfo,
  val self: Boolean,
  val voice: Boolean
) : Parcelable

@Parcelize
data class Country(
  val name: String,
  val code: String,
  val timezone: String
) : Parcelable

@Parcelize
data class Image(
  val medium: String,
  val original: String?
) : Parcelable

@Parcelize
data class CharacterInfo(
  val id: Int,
  val url: String,
  val name: String,
  val image: Image?,
  val _links: Links
) : Parcelable

@Parcelize
data class Links(
  val self: Self
) : Parcelable

@Parcelize
data class Self(
  val href: String
) : Parcelable