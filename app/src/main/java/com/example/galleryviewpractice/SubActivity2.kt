package com.example.galleryviewpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.galleryviewpractice.databinding.ActivitySub2Binding
import io.realm.Realm
import io.realm.kotlin.where

class SubActivity2 : AppCompatActivity() {

    private lateinit var realm:Realm
    private lateinit var binding:ActivitySub2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub2)
        realm=Realm.getDefaultInstance()
        binding= ActivitySub2Binding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        binding.list.layoutManager=LinearLayoutManager(this)
        val img=realm.where<Images>().findAll()
        val adapter=RecyclerAdapter(img)
        binding.list.adapter=adapter

        adapter.setOnItemClickListener { id->
            realm.executeTransaction { db:Realm->
                db.where<Images>().findFirst()?.deleteFromRealm()
            }
            Toast.makeText(this,"done",Toast.LENGTH_SHORT)
        }
    }
}