package com.example.appcocktails.domain.repository

import com.example.appcocktails.domain.model.Cocktail

interface CocktailRepository {
    suspend fun getCocktails(): List<Cocktail>
    suspend fun getCocktailById(id: String): Cocktail?
}