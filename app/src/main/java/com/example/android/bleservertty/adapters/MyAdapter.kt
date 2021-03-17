package com.example.android.bleservertty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.bleservertty.R
import com.example.android.bleservertty.data.Faculty

class MyAdapter(var  dataList: MutableList<Faculty>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.faculty_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        holder.desc.text = dataList[position].Email
        holder.title.text = dataList[position].Name
        holder.imageIcon.setImageResource(R.drawable.propicblank)
    }

    override fun getItemCount() = dataList.size

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        var title: TextView = view.findViewById(R.id.title)
        var desc: TextView = view.findViewById(R.id.description)
        var imageIcon: ImageView = view.findViewById(R.id.imageView)
    }
}