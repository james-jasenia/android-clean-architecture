package com.example.andrdoid_clean_architecture.data.remote

import com.example.andrdoid_clean_architecture.data.remote.dto.BeerDto
import retrofit2.http.GET

interface PunkApi {

    @GET("/v2/beers")
    suspend fun getBeers(): List<BeerDto>
}