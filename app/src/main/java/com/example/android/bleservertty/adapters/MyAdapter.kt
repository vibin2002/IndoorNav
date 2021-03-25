package com.example.android.bleservertty.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.bleservertty.R
import com.example.android.bleservertty.data.Faculty

class MyAdapter(var  dataList: MutableList<Faculty>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(), Filterable {

    var dataListAll = mutableListOf<Faculty>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        dataListAll = dataList.toCollection(mutableListOf())
        Log.d("DocStrange", dataListAll.toString())
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

    override fun getFilter() = myFilter()

    private fun myFilter(): Filter{
        return object : Filter()
        {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterList = mutableListOf<Faculty>()
                if (constraint == null || constraint.isEmpty())
                    filterList.addAll(dataListAll)
                else{
                    val pattern = constraint.toString().toLowerCase().trim()
                    for (item in dataListAll)
                    {
                        if (item.Email.toLowerCase().contains(pattern)
                            || item.Name.toLowerCase().contains(pattern))
                        {
                            filterList.add(item)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraints: CharSequence?, result: FilterResults?) {
                dataList.clear()
                dataList.addAll(result?.values as MutableList<Faculty>)
                notifyDataSetChanged()
            }
        }
    }
}