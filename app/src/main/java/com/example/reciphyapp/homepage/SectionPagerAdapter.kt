package com.example.reciphyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.reciphyapp.database.ListRecomResponseItem
import com.example.reciphyapp.homepage.FragmentRecommend

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var tab1: List<ListRecomResponseItem>? = null

    override fun createFragment(position: Int): Fragment {
        val fragment = FragmentRecommend()
        fragment.arguments = Bundle().apply {
            putInt(FragmentRecommend.ARG_POSITION, position + 1)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 4
    }
}