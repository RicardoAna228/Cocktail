package com.example.appcocktails.domain.model

data class Cocktail(
    val id: String,
    val name: String,
    val category: String,
    val instructions: String,
    val thumbUrl: String,
    val glass: String
)