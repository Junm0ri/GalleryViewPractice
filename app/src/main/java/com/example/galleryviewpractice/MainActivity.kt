package com.example.galleryviewpractice

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        binding.Sub2Button.setOnClickListener {
            val intent=Intent(this,SubActivity2::class.java)
            startActivity(intent)
        }
        binding.Sub3Button.setOnClickListener {
            val intent=Intent(this,SubActivity3::class.java)
            startActivity(intent)
        }
        binding.deleteButton.setOnClickListener {
            realm.executeTransaction {
                realm.where<Images>().findAll().deleteAllFromRealm()
            }
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

        val myedit = EditText(this)
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("文字を入力してください")
        dialog.setView(myedit)
        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
            val userText = myedit.getText().toString()
            Toast.makeText(this, "$userText と入力しました", Toast.LENGTH_SHORT).show()
            //DBに追加
            realm.executeTransaction { db:Realm->
                val maxId=db.where<Images>().max("id")
                val nextId=(maxId?.toLong() ?:0L)+1L
                val data=db.createObject<Images>(nextId)
                data.tag=userText
                val baos=ByteArrayOutputStream()
                image?.compress(Bitmap.CompressFormat.JPEG,100,baos)
                val imageByteArray=baos.toByteArray()
                data.image=imageByteArray
            }
        })
        dialog.setNegativeButton("キャンセル", null)
        dialog.show()
    }

}