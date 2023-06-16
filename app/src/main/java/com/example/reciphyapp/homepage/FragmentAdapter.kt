package com.example.reciphyapp.homepage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reciphyapp.R
import com.example.reciphyapp.database.ListRecomResponseItem
import com.example.reciphyapp.detail.DetailActivity

class FragmentAdapter(private var context : Context,
                      private var listRecipe: List<ListRecomResponseItem>)
    : RecyclerView.Adapter<FragmentAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reTitle = view.findViewById<TextView>(R.id.tv_tabhomepage)
        val reImg = view.findViewById<ImageView>(R.id.iv_tabhomepage)
        val cvTab = view.findViewById<CardView>(R.id.cv_imgTabhomepage)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder( LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_item, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(context).load(listRecipe[position].url).into(viewHolder.reImg)
        viewHolder.reTitle.text = listRecipe[position].title
        viewHolder.cvTab.setOnClickListener{
            val intentDetail = Intent(viewHolder.cvTab.context, DetailActivity::class.java)
            intentDetail.putExtra("id_key", listRecipe[position].id)
            viewHolder.cvTab.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listRecipe.size
}