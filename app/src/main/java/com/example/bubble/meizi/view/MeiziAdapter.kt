package com.example.bubble.meizi.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bubble.meizi.R
import com.example.bubble.meizi.model.Girl
import kotlinx.android.synthetic.main.meizi_item.view.*
import kotlin.random.Random

class MeiziAdapter(context: Context, meizis: MutableList<Girl>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mContext = context
    private var girlList = meizis
    private val constraintSet = ConstraintSet()
    inner class MeiziHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mConstraintLayout: ConstraintLayout = itemView.findViewById(R.id.parentContraint)
        val girlImage: ImageView = itemView.findViewById(R.id.girlIv)
        val descTv: TextView = itemView.findViewById(R.id.descTv)
    }
    private val ratios = arrayOf("3:5","9:16", "5:4");

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val view = LayoutInflater.from(mContext).inflate(R.layout.meizi_item, parent, false)
        return MeiziHolder(view)
    }

    override fun getItemCount(): Int {
        return girlList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val meizi = girlList[position]
        val updatedUrl = meizi.url.replace("http", "https");
        Log.d("Meizi", updatedUrl)
        val ratio = ratios[(0..2).random()]
        Log.d("Meizi", ratio)
        if(holder is MeiziHolder){
            with(meizi){
                holder.descTv.text = desc
                Glide.with(holder.itemView.context)
                    .load(updatedUrl)
                    .into(holder.girlImage)
//                with(constraintSet) {
//                    clone(holder.mConstraintLayout)
//                    setDimensionRatio(holder.girlImage.id, ratio)
//                    applyTo(holder.mConstraintLayout)
//                }
            }

        }


    }
}