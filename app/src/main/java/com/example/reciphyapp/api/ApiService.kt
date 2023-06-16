package com.example.reciphyapp.api

import com.example.reciphyapp.database.DetailResponse
import com.example.reciphyapp.database.ListRecomResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/getRecommendation")
    fun getListRecommendation(@Query ("input") input : String): Call<List<ListRecomResponseItem>>

    @GET("/getDetailRecipe")
    fun getDetail(@Query ("id") input : String): Call<DetailResponse>

    @GET("/getByTag")
    fun getTag(@Query ("tag") tag : String): Call<List<ListRecomResponseItem>>
}