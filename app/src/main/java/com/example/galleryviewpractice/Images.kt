package com.example.galleryviewpractice

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Images:RealmObject() {
    @PrimaryKey
    var id:Long=0
    public open var image:ByteArray?=null
    var tag:String=""
}