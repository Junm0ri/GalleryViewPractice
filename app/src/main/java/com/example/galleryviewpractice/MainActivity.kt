package com.example.galleryviewpractice

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.galleryviewpractice.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var realm:Realm
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        realm=Realm.getDefaultInstance()
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)

        binding.selectButton.setOnClickListener {
            selectPhoto()
        }
        binding.subButton.setOnClickListener {
            val intent=Intent(this,SubActivity::class.java)
            startActivity(intent)
        }
    }

    private fun selectPhoto() {
        val intent=Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type="image/*"
        }
        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    companion object {
        private const val READ_REQUEST_CODE:Int=42
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            READ_REQUEST_CODE -> {
                try {
                    resultData?.data?.also { uri ->
                        val inputStream = contentResolver?.openInputStream(uri)
                        val image = BitmapFactory.decodeStream(inputStream)
                        val imageView = findViewById<ImageView>(R.id.imageView)

//                        DBに保存した後画面に表示する
                        saveItem(image)
                        imageView.setImageBitmap(image)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "エラーが発生しました", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun saveItem(image: Bitmap?) {
        realm.executeTransaction { db:Realm->
            val maxId=db.where<Images>().max("id")
            val nextId=(maxId?.toLong() ?:0L)+1L
            val data=db.createObject<Images>(nextId)
            data.tag="(description of image)"
            image?.compress(Bitmap.CompressFormat.PNG,100,ByteArrayOutputStream())
            val imageByteArray=ByteArrayOutputStream().toByteArray()
            data.image=imageByteArray
        }
    }
}