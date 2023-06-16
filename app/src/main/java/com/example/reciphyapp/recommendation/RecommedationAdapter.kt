package com.example.reciphyapp.recommendation

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reciphyapp.database.ListRecomResponseItem
import com.example.reciphyapp.databinding.RvItemRecommendBinding
import com.example.reciphyapp.detail.DetailActivity

class RecommedationAdapter : RecyclerView.Adapter<RecommedationAdapter.ViewHolder>() {

    var listRecipe = ArrayList<ListRecomResponseItem>()

    fun setData(data: ArrayList<ListRecomResponseItem>) {
        this.listRecipe.clear()
        this.listRecipe.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding : RvItemRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: ListRecomResponseItem){
            binding.apply {
                tvSearch.text = recipe.title
                Glide.with(itemView).load(recipe.url).into(ivSearch)
            }
            binding.cvSearch.setOnClickListener{
                val intentDetail = Intent(binding.cvSearch.context, DetailActivity::class.java)
                intentDetail.putExtra("id_key", recipe.id)
                binding.cvSearch.context.startActivity(intentDetail)
            }

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) :ViewHolder {
        val view = RvItemRecommendBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listRecipe[position])
    }

    override fun getItemCount(): Int = listRecipe.size
}