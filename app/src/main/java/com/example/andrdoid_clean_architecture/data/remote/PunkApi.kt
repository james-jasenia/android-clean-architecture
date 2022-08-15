package com.example.andrdoid_clean_architecture.data.remote

import com.example.andrdoid_clean_architecture.data.remote.dto.BeerDto
import retrofit2.Response
import retrofit2.http.GET

interface PunkApi {

    @GET("beers/")
    suspend fun getBeers(): Response<List<BeerDto>>
}