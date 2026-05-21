package com.example.appcocktails.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {

    @GET("search.php")
    suspend fun searchCocktails(
        @Query("f") letter: String = "a"
    ): CocktailListResponse

    @GET("lookup.php")
    suspend fun getCocktailById(
        @Query("i") id: String
    ): CocktailListResponse
}