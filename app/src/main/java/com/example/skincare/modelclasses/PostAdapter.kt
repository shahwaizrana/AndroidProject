package com.example.skincare.modelclasses

import android.app.Activity
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.skincare.R
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.blog_view_recyclerlayout.view.*

class PostAdapter(val blogs: PostData, val activity: Activity) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.blog_view_recyclerlayout
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.txtblogtitle.text = blogs.title
        viewHolder.itemView.number.text = blogs.number
        viewHolder.itemView.txtblogwritten.text ="Address "+ blogs.description
        viewHolder.itemView.txtblogtimestamp.text = blogs.postDateTime
        viewHolder.itemView.status.text = "Status "+blogs.status
        viewHolder.itemView.txtuid.text = blogs.userid
//        Picasso.get().load(blogs.blogimageUrl).into(viewHolder.itemView.imgblog)
//        Picasso.get().load(blogs.userimage).into(viewHolder.itemView.imgblogger)

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.loading)
        Glide.with(activity).load(blogs.blogimageUrl).apply(requestOptions)
            .into(viewHolder.itemView.imgblog)

        Glide.with(activity).load(blogs.userimage).apply(requestOptions)
            .into(viewHolder.itemView.imgblogger)


    }

}