package com.example.galleryviewpractice

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.galleryviewpractice.databinding.ActivitySubBinding
import io.realm.Realm
import io.realm.kotlin.where

class SubActivity : AppCompatActivity() {

    private lateinit var realm:Realm
    private lateinit var binding:ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        realm=Realm.getDefaultInstance()
        binding= ActivitySubBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val content=realm.where<Images>().findFirst()
        var CI=content?.image
        binding.textView.text=content?.tag
        binding.textView2.text=CI.toString()
//        binding.imageView2.setImageBitmap(content?.image)
        val BMP=BitmapFactory.decodeByteArray(CI,0,CI!!.size)
        binding.imageView2.setImageBitmap(BMP)
//        binding.imageView2=BitmapFactory.decodeByteArray(CI,0,content?.image!!.size)
//        Glide.with(this).load(content?.image).into(binding.imageView2)
    }
}