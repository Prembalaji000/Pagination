package com.example.pagination.data.di.model

data class ImageResponse(
    val hits: List<ImageDTO>,
    val total: Int,
    val totalHits: Int
)