package com.example.andrdoid_clean_architecture.domain.use_cases

import com.example.andrdoid_clean_architecture.domain.model.Beer
import com.example.andrdoid_clean_architecture.domain.repositories.BeersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(
    private val repository: BeersRepository
) {
    suspend operator fun invoke() : Result<List<Beer>> {
        return try {
            val beers = repository.getBeers()
            Result.success(beers)
        } catch(error: IOException) {
            Result.failure(error)
        } catch(error: HttpException) {
            Result.failure(error)
        }
    }
}