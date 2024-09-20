package com.example.assignment2rentwithintent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicalEquipment(
    val name: String,
    val rating: Float,
    val availability: Boolean,
    val price: Int,
    val imgID: Int
    ): Parcelable