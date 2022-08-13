package com.example.andrdoid_clean_architecture.data.repositories

import com.example.andrdoid_clean_architecture.data.remote.PunkApi
import com.example.andrdoid_clean_architecture.data.remote.dto.toBeer
import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.repositories.BeersRepository
import javax.inject.Inject


class BeersRepositoryImpl @Inject constructor(
    private val api: PunkApi
) : BeersRepository {
    override suspend fun getBeers(): List<Beer> {
        val beers = api.getBeers()
        return beers.map { it.toBeer() }
    }
}