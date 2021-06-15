package com.example.skincare.modelclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PostData(val id:String,val userid:String,val title:String,val number:String,val description:String,
               val blogimageUrl:String,val userimage:String,val postDateTime:String,val status: String):
    Parcelable {

    constructor():this("","","","","","","","","");
}

