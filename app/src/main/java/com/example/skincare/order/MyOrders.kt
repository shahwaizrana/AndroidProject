package com.example.skincare.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skincare.R
import com.example.skincare.modelclasses.PostAdapter
import com.example.skincare.modelclasses.PostData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_my_orders.*

class MyOrders : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)
        auth = FirebaseAuth.getInstance();
        fetch_Data();
    }


    private fun fetch_Data() {
        val ref = FirebaseDatabase.getInstance().getReference("/postOrder/"+auth.uid)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }


            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach {

                    val blog = it.getValue(PostData::class.java)
                    if (blog != null) {
                        adapter.add(PostAdapter(blog,this@MyOrders))
                    }

                }
                myorders_recycler_view.adapter = adapter

            }


        })
    }
}