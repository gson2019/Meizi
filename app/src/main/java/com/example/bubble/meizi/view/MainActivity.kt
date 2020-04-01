package com.example.bubble.meizi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bubble.meizi.R
import com.example.bubble.meizi.viewmodel.MeiziViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var meiziViewModel: MeiziViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        meiziViewModel = ViewModelProvider(this).get(MeiziViewModel::class.java)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        meiziRv.layoutManager = staggeredGridLayoutManager
        meiziViewModel.content.observe(this, Observer {
            meiziRv.adapter = MeiziAdapter(this, it.toMutableList())
        })
    }
}
