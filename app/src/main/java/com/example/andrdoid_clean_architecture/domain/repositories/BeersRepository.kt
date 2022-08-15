package com.example.andrdoid_clean_architecture.domain.repositories

import com.example.andrdoid_clean_architecture.domain.model.Beer
import retrofit2.Response

interface BeersRepository {
    suspend fun getBeers(): Result<List<Beer>>
}