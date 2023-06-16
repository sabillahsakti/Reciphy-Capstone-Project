package com.example.reciphyapp.database

import com.google.gson.annotations.SerializedName

data class ListRecomResponse(

	@field:SerializedName("ListRecomResponse")
	val listRecomResponse: List<ListRecomResponseItem>
)

data class ListRecomResponseItem(

	@field:SerializedName("loves")
	val loves: Int,

	@field:SerializedName("ingredients")
	val ingredients: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tag")
	val tag: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("steps")
	val steps: String,

	@field:SerializedName("url")
	val url: String
)
