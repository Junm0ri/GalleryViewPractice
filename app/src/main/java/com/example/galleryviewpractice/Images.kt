package com.example.galleryviewpractice

import android.graphics.Bitmap
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Images:RealmObject() {
    @PrimaryKey
    var id:Long=0
    var image:ByteArray?=null
    var tag:String=""
}