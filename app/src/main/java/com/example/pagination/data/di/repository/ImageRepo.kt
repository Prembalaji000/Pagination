package com.example.pagination.data.di.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.pagination.domain.models.Image
import com.example.pagination.domain.models.repository.ImageRepository
import com.example.pagination.data.di.mapper.ImageMapper
import com.example.pagination.data.di.pagingSource.ImagePagingSource
import com.example.pagination.data.di.remote.ApiService
import jakarta.inject.Inject

class ImageRepo @Inject constructor(
    private val apiService : ApiService,
    private val mapper : ImageMapper
) : ImageRepository {
    override fun getImage(q: String): Pager<Int, Image> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                ImagePagingSource(
                    q = q,
                    apiService = apiService,
                    mapper = mapper
                )
            }
        )
    }
}