package com.example.assignment2rentwithintent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicalEquipment(
    val name: String,
    val strapOption: Boolean,
    val rating: Float,
    val availability: Boolean,
    val price: Int, //this will be the total price for the 12 month rental
    val imgID: Int,
    val bookedImgId: Int,
    val desc: String,
    var strapSelected: Boolean=false,
    var itemBooked: Boolean=false
    //var bookedImgID: Int
    ): Parcelable