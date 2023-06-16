package com.example.reciphyapp.recommendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reciphyapp.R
import com.example.reciphyapp.api.ApiConfig
import com.example.reciphyapp.database.ListRecomResponseItem
import com.example.reciphyapp.databinding.ActivityRecommendationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecommendationBinding
    private lateinit var ingredients : String
    private lateinit var adapter: RecommedationAdapter
    private lateinit var input: EditText

    companion object {
        var TAG = "RecommendationActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecommedationAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvRecommendation.setHasFixedSize(true)
            rvRecommendation.layoutManager = LinearLayoutManager(this@RecommendationActivity)
            rvRecommendation.adapter = adapter
        }

        val input = findViewById<EditText>(R.id.et_input)
        binding.btnInput.setOnClickListener {
            ingredients = input.text.toString()
            findIngredients()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun findIngredients() {
        showLoading(true)
        val client = ApiConfig.getApiDatabase().getListRecommendation(ingredients)
        client.enqueue(object : Callback<List<ListRecomResponseItem>> {
            override fun onResponse(
                call: Call<List<ListRecomResponseItem>>,
                response: Response<List<ListRecomResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val dataRecipe = ArrayList(responseBody)
                        adapter.setData(dataRecipe)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ListRecomResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}