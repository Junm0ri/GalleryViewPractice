package com.example.galleryviewpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        binding.textView.text=content?.tag
    }
}