package com.example.pagination.domain.models.useCase

import com.example.pagination.domain.models.repository.ImageRepository
import jakarta.inject.Inject

class GetImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
){
    operator fun invoke(q : String) = imageRepository.getImage(q = q)
}