package com.example.appcocktails.data.repository

import com.example.appcocktails.data.local.CocktailDao
import com.example.appcocktails.data.local.CocktailEntity
import com.example.appcocktails.data.remote.CocktailApiService
import com.example.appcocktails.domain.model.Cocktail
import com.example.appcocktails.domain.repository.CocktailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CocktailRepositoryImpl(
    private val api: CocktailApiService,
    private val dao: CocktailDao
) : CocktailRepository {

    override suspend fun getCocktails(): List<Cocktail> = withContext(Dispatchers.IO) {
        try {
            val remote = api.searchCocktails().drinks ?: emptyList()
            val entities = remote.map {
                CocktailEntity(
                    id           = it.id,
                    name         = it.name,
                    category     = it.category ?: "",
                    instructions = it.instructions ?: "",
                    thumbUrl     = it.thumbUrl ?: "",
                    glass        = it.glass ?: ""
                )
            }
            dao.insertAll(entities)          // Guarda en caché
            entities.map { it.toDomain() }
        } catch (e: Exception) {
            // Sin internet → retorna caché local
            dao.getAllCocktails().map { it.toDomain() }
        }
    }

    override suspend fun getCocktailById(id: String): Cocktail? = withContext(Dispatchers.IO) {
        try {
            val dto = api.getCocktailById(id).drinks?.firstOrNull()
            dto?.let {
                val entity = CocktailEntity(it.id, it.name, it.category ?: "",
                    it.instructions ?: "", it.thumbUrl ?: "", it.glass ?: "")
                dao.insertAll(listOf(entity))
                entity.toDomain()
            }
        } catch (e: Exception) {
            dao.getCocktailById(id)?.toDomain()
        }
    }

    private fun CocktailEntity.toDomain() = Cocktail(id, name, category, instructions, thumbUrl, glass)
}