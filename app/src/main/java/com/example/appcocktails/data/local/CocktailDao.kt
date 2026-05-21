package com.example.appcocktails.data.local

import androidx.room.*

@Dao
interface CocktailDao {

    @Query("SELECT * FROM cocktails")
    suspend fun getAllCocktails(): List<CocktailEntity>

    @Query("SELECT * FROM cocktails WHERE id = :id")
    suspend fun getCocktailById(id: String): CocktailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cocktails: List<CocktailEntity>)
}