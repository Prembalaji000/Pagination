package com.example.pagination.domain.models

import java.util.UUID

data class Image(
    val id : String,
    val imageUrl : String,
    val uuId : String = UUID.randomUUID().toString()
)