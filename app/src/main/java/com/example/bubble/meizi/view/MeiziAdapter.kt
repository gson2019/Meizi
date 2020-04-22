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
import com.example.bubble.meizi.model.Hit
import kotlinx.android.synthetic.main.meizi_item.view.*
import org.w3c.dom.Text
import kotlin.random.Random

class MeiziAdapter(context: Context,
                   meizis: MutableList<Hit>,
                   val hitMap: HashMap<Int, Boolean>,
                   onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mContext = context
    private var girlList = meizis
    private val constraintSet = ConstraintSet()
    private val onClickListener: OnItemClickListener = onItemClickListener
    inner class MeiziHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mConstraintLayout: ConstraintLayout = itemView.findViewById(R.id.parentContraint)
        val girlImage: ImageView = itemView.findViewById(R.id.girlIv)
        val descTv: TextView = itemView.findViewById(R.id.descTv)
        val likeTv: TextView = itemView.findViewById(R.id.likeCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val view = LayoutInflater.from(mContext).inflate(R.layout.meizi_item, parent, false)
        return MeiziHolder(view)
    }

    override fun getItemCount(): Int {
        return girlList.size
    }

    interface OnItemClickListener {
        fun onClick(hit: Hit, position: Int)
    }

    fun addHits(hits: List<Hit>) {
        girlList = hits.toMutableList()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val meizi = girlList[position]
        if(holder is MeiziHolder){
            holder.itemView.saveToggle.isChecked = hitMap[meizi.id]!!
            holder.itemView.saveToggle.setOnClickListener {
                onClickListener.onClick(meizi, position)
            }
            with(meizi){
                val ratio = String.format("%d:%d", imageWidth, imageHeight)
                holder.descTv.text = tags
                holder.likeTv.text = likes.toString()
                Glide.with(holder.itemView.context)
                    .load(largeImageURL)
                    .into(holder.girlImage)
                with(constraintSet) {
                    clone(holder.mConstraintLayout)
                    setDimensionRatio(holder.girlImage.id, ratio)
                    applyTo(holder.mConstraintLayout)
                }
            }
        }
    }
}