package com.example.appcocktails.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val instructions: String,
    val thumbUrl: String,
    val glass: String
)
