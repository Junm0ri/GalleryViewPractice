package com.example.galleryviewpractice

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.galleryviewpractice.databinding.ActivitySub3Binding
import io.realm.Realm
import io.realm.kotlin.where

class SubActivity3 : AppCompatActivity() {

    private lateinit var realm:Realm
    private lateinit var binding:ActivitySub3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub3)
        realm=Realm.getDefaultInstance()
        binding= ActivitySub3Binding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val content=realm.where<Images>().findFirst()
        var CI=content?.image
        val BMP=BitmapFactory.decodeByteArray(CI,0,CI!!.size)
        binding.imageView4.setImageBitmap(BMP)
    }
}