package com.example.andrdoid_clean_architecture.di

import android.provider.SyncStateContract
import com.example.andrdoid_clean_architecture.data.remote.PunkApi
import com.example.andrdoid_clean_architecture.data.repositories.BeersRepositoryImpl
import com.example.andrdoid_clean_architecture.domain.repositories.BeersRepository
import com.example.andrdoid_clean_architecture.domain.use_cases.GetBeersUseCase
import com.example.andrdoid_clean_architecture.utils.Constants
import com.example.andrdoid_clean_architecture.utils.DispatcherProvider
import com.example.andrdoid_clean_architecture.utils.StandardDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePunkApi(): PunkApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PunkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBeersRepository(api: PunkApi) : BeersRepository {
        return BeersRepositoryImpl(api )
    }

    @Provides
    @Singleton
    fun providesGetBeersUseCase(repository: BeersRepository) : GetBeersUseCase {
        return GetBeersUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesStandardDispatchers() : DispatcherProvider {
        return StandardDispatchers()
    }

}