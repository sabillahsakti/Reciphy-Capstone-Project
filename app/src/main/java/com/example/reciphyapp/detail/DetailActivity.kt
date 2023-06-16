package com.example.reciphyapp.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.reciphyapp.R
import com.example.reciphyapp.database.DetailResponse
import com.example.reciphyapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var recipeId: String
    private lateinit var viewModel: DetailViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.ingredients,
            R.string.steps
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = findViewById(R.id.viewpager_detail)
        val tabs: TabLayout = findViewById(R.id.tabs)
        val detailSectionsPagerAdapter = DetailSectionPageAdapter(this)
        viewPager.adapter = detailSectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        val intent = intent
        val getId = intent.getIntExtra("id_key", 0)
        recipeId = getId.toString()

        viewModel.findDetail(ID = recipeId)
        viewModel.recipe.observe(this){ recipe ->
            binding.tvTitleDetail.text = recipe?.title
            Glide.with(this@DetailActivity)
                .load(recipe?.url)
                .into(binding.ivImgDetail)
            detailSectionsPagerAdapter.ingredients = recipe?.ingredients.toString()
            detailSectionsPagerAdapter.steps = recipe?.steps.toString()
            }

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}
