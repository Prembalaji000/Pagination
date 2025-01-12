package com.example.pagination.data.di.mapper

import com.example.pagination.domain.models.Image
import com.example.pagination.data.di.model.ImageDTO
import jakarta.inject.Inject

class ImageMapper @Inject constructor() : Mapper<ImageDTO, Image> {
    override fun map(from: ImageDTO): Image {
        return Image(
            id = from.id.toString(),
            imageUrl = from.userImageURL
        )
    }
}