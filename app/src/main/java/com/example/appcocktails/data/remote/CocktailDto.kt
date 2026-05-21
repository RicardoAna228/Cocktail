package com.example.appcocktails.data.remote

import com.google.gson.annotations.SerializedName

data class CocktailListResponse(
    @SerializedName("drinks") val drinks: List<CocktailDto>?
)

data class CocktailDto(
    @SerializedName("idDrink")       val id: String,
    @SerializedName("strDrink")      val name: String,
    @SerializedName("strCategory")   val category: String?,
    @SerializedName("strInstructions") val instructions: String?,
    @SerializedName("strDrinkThumb") val thumbUrl: String?,
    @SerializedName("strGlass")      val glass: String?
)