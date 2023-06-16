package com.example.reciphyapp.homepage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reciphyapp.MainActivity
import com.example.reciphyapp.api.ApiConfig
import com.example.reciphyapp.database.ListRecomResponseItem
import com.example.reciphyapp.databinding.FragmentRecommendBinding
import com.example.reciphyapp.recommendation.RecommedationAdapter
import com.example.reciphyapp.recommendation.RecommendationActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentRecommend : Fragment() {
    private var position: Int? = null
    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRecommend.layoutManager = LinearLayoutManager(requireActivity())

        arguments?.let {
            position = it.getInt(ARG_POSITION, 0)
        }

        when {
            position == 1 -> findRecommend1(tags = "Sapi")
            position == 2 -> findRecommend1(tags = "Ayam")
            position == 3 -> findRecommend1(tags = "Ikan")
            position == 4 -> findRecommend1(tags = "Telur")
        }
    }

    private fun findRecommend1(tags : String) {
        showLoading(true)
        val client = ApiConfig.getApiDatabase().getTag(tags)
        client.enqueue(object : Callback<List<ListRecomResponseItem>> {
            override fun onResponse(
                call: Call<List<ListRecomResponseItem>>,
                response: Response<List<ListRecomResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val baseContext = context
                        val adapter = baseContext?.let { FragmentAdapter(it, responseBody) }
                        binding.rvRecommend.adapter = adapter
                    }
                } else {
                    Log.e(RecommendationActivity.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ListRecomResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e(RecommendationActivity.TAG, "onFailure: ${t.message}")
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
        findRecommend1(tags = "sapi")
    }

    companion object {
        val ARG_POSITION = "position"
    }
}