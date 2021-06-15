package com.example.skincare.modelclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserData(
    val uid: String, val userName: String, val userEmail: String,
    val userGender: String
) :
    Parcelable {

    constructor() : this("", "", "", "");
}
