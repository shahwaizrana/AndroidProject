package com.example.skincare.Fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.skincare.R
import com.google.android.material.tabs.TabLayout

class FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var tab_tablayout: TabLayout? = null
        var tab_viewpager: ViewPager? = null
        setContentView(R.layout.activity_fragment)


        // Create the object of Toolbar, ViewPager and
        // TabLayout and use “findViewById()” method*/
        var tab_toolbar = findViewById<Toolbar>(R.id.toolbar_fragment)
        tab_viewpager = findViewById<ViewPager>(R.id.tab_viewpager)
        tab_tablayout = findViewById<TabLayout>(R.id.tab_tablayout)

        tab_tablayout.addTab(tab_tablayout.newTab().setText("Description"))
        tab_tablayout!!.addTab(tab_tablayout!!.newTab().setText("Causes"))

        tab_tablayout.addTab(tab_tablayout.newTab().setText("Syptoms"))
        tab_tablayout.addTab(tab_tablayout.newTab().setText("Prevention"))
        tab_tablayout.addTab(tab_tablayout.newTab().setText("Treatment"))
        tab_tablayout!!.tabGravity = TabLayout.GRAVITY_FILL
        tab_tablayout!!.tabMode = TabLayout.MODE_SCROLLABLE

        // As we set NoActionBar as theme to this activity
        // so when we run this project then this activity doesn't
        // show title. And for this reason, we need to run
        // setSupportActionBar method
        setSupportActionBar(tab_toolbar)


        // If we dont use setupWithViewPager() method then
        // tabs are not used or shown when activity opened

        tab_tablayout!!.setupWithViewPager(tab_viewpager)


        var adapter: FragmentAdapter = FragmentAdapter(this, supportFragmentManager, tab_tablayout.tabCount)

        tab_viewpager.adapter=adapter;
    }


}