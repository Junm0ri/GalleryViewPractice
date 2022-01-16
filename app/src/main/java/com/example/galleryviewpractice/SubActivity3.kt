package com.example.galleryviewpractice

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.galleryviewpractice.databinding.ActivitySub3Binding
import io.realm.Realm
import io.realm.kotlin.where
import kotlin.concurrent.timer

class SubActivity3 : AppCompatActivity() {

    private lateinit var realm:Realm
    private lateinit var binding:ActivitySub3Binding
    private var TM=timer(period=1000){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub3)
        realm=Realm.getDefaultInstance()
        binding= ActivitySub3Binding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val maxId=realm.where<Images>().max("id")
        var id=-1L

        TM=timer(period=1000) {
            runOnUiThread {
                if (id==maxId) id=-1L
                var content=realm.where<Images>().greaterThan("id",id).findFirst()
                id=content!!.id
                val CI=content?.image
                val BMP=BitmapFactory.decodeByteArray(CI,0,CI!!.size)
                binding.imageView4.setImageBitmap(BMP)
            }
        }




    }
}