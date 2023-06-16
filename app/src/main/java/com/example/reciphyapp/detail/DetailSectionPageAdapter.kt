package com.example.reciphyapp.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailSectionPageAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var ingredients: String = ""
    var steps: String = ""

    override fun createFragment(position: Int): Fragment {
        return if (position == 1){
            DetailFragment()

        } else {
            IngredientsFragment()
        }

    }
    override fun getItemCount(): Int {
        return 2
    }
}