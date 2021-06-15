package com.example.skincare.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skincare.Fragments.FragmentModel
import com.example.skincare.R
import kotlinx.android.synthetic.main.causes_recycler_singleitemview.view.*

class CausesAdapter(context:Context, val list: List<FragmentModel>) : RecyclerView.Adapter<CausesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):CausesAdapter. ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.causes_recycler_singleitemview, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
      return list.size;

    }

    override fun onBindViewHolder(holder: CausesAdapter.ViewHolder, position: Int) {


            holder.itemView.causes_singleitem.text=list[position].title
        holder.itemView.causes_item_description.text=list[position].description

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}