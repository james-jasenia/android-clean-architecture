package com.example.andrdoid_clean_architecture.di

import android.provider.SyncStateContract
import com.example.andrdoid_clean_architecture.data.remote.PunkApi
import com.example.andrdoid_clean_architecture.data.repositories.BeersRepositoryImpl
import com.example.andrdoid_clean_architecture.domain.repositories.BeersRepository
import com.example.andrdoid_clean_architecture.utils.Constants
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
}