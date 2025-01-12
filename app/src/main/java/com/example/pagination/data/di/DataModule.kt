package com.example.pagination.data.di

import com.example.pagination.domain.models.repository.ImageRepository
import com.example.pagination.data.di.mapper.ImageMapper
import com.example.pagination.data.di.remote.ApiService
import com.example.pagination.data.di.repository.ImageRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    //https://pixabay.com/api/?key=48185039-a4897d229949cadf17c51387d&q=yellow+flowers&image_type=photo

    @Provides
    @Singleton
    fun provideRepository(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pixabay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    fun provideApiService(retrofit : Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideImageRepository(apiService: ApiService, mapper: ImageMapper): ImageRepository {
        return ImageRepo(apiService = apiService, mapper = mapper)
    }
}