package com.example.galleryviewpractice

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class RecyclerAdapter(data:OrderedRealmCollection<Images>): RealmRecyclerViewAdapter<Images,RecyclerAdapter.ViewHolder>(data,true){

    private var listener: ((Long?)->Unit)?=null
    fun setOnItemClickListener(listener:(Long?)->Unit) {
        this.listener=listener
    }

    init {
        setHasStableIds(true)
    }

    class ViewHolder(cell: View):RecyclerView.ViewHolder(cell) {
        val text: TextView =cell.findViewById(R.id.itemTextView)
        val image:ImageView=cell.findViewById(R.id.itemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val img:Images?=getItem(position)
        holder.text.text=img?.tag
        val BMP= BitmapFactory.decodeByteArray(img?.image,0,img?.image!!.size)
        holder.image.setImageBitmap(BMP)
        holder.itemView.setOnClickListener {
            listener?.invoke(img?.id)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?:0
    }
}