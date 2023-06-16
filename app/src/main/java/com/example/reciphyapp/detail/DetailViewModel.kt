package com.example.reciphyapp.detail

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reciphyapp.api.ApiConfig
import com.example.reciphyapp.database.DetailResponse
import com.example.reciphyapp.recommendation.RecommendationActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _recipe = MutableLiveData<DetailResponse?>()
    val recipe: LiveData<DetailResponse?> = _recipe

    private val _steps = MutableLiveData<CharSequence?>()
    val steps: LiveData<CharSequence?> = _steps

    private val _ingredients = MutableLiveData<CharSequence?>()
    val ingredients: LiveData<CharSequence?> = _ingredients

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findDetail(ID : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiDatabase().getDetail(ID)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _recipe.value = responseBody
                        setIngredients(responseBody)
                        setSteps(responseBody)
                    }
                } else {
                    Log.e(RecommendationActivity.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(RecommendationActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun setSteps(body: DetailResponse) {
        val steps = body?.steps
        val delim = "--"
        val listSteps = steps?.split(delim)
        _steps.value = listSteps?.let { convertToBulletList(it) }
    }

    fun setIngredients(body: DetailResponse) {
        val ingredients = body?.ingredients
        val delim = "--"
        val listIngredients = ingredients?.split(delim)
        _ingredients.value = listIngredients?.let { convertToBulletList(it) }
    }

    fun convertToBulletList(stringList: List<String>): CharSequence {
        val spannableStringBuilder = SpannableStringBuilder("\n")
        stringList.forEachIndexed { index, text ->
            val line: CharSequence = text + (if (index < stringList.size - 1) "\n" else "")
            val spannable: Spannable = SpannableString(line)
            spannable.setSpan(
                BulletSpan(15, Color.BLACK),
                0,
                spannable.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            spannableStringBuilder.append(spannable)
        }
        return spannableStringBuilder
    }

}