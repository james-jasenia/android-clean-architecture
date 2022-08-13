package com.example.andrdoid_clean_architecture.domain.repositories

import com.example.andrdoid_clean_architecture.domain.model.Beer

interface BeersRepository {

    suspend fun getBeers(): List<Beer>

}