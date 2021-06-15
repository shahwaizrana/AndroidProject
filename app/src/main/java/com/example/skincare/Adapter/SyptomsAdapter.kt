package com.example.skincare.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skincare.Fragments.FragmentModel
import com.example.skincare.R
import kotlinx.android.synthetic.main.types_recycler_single_itemview.view.*

class SyptomsAdapter(context: Context, val list: List<FragmentModel>): RecyclerView.Adapter<SyptomsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.types_recycler_single_itemview, parent, false)
        return SyptomsAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.itemView.remedies_item.text=list[position].title;
            holder.itemView.remedies_item_description.text=list[position].description;

    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

}