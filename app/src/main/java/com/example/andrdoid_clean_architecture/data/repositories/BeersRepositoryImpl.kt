package com.example.andrdoid_clean_architecture.data.repositories

import com.example.andrdoid_clean_architecture.data.remote.PunkApi
import com.example.andrdoid_clean_architecture.data.remote.dto.toBeer
import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.repositories.BeersRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class BeersRepositoryImpl @Inject constructor(
    private val api: PunkApi
) : BeersRepository {
    override suspend fun getBeers(): Result<List<Beer>> {
        val response = try {
            api.getBeers()
        } catch(error: IOException) {
            return Result.failure(error)
        } catch(error: HttpException) {
            return Result.failure(error)
        }

        return if(response.isSuccessful && response.body() != null) {
            val beers = response.body()!!.map { it.toBeer() }
            Result.success(beers)
        } else {
            Result.failure(Error())
        }
    }
}