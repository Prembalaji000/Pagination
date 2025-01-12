package com.example.pagination.domain.models.repository

import androidx.paging.Pager
import com.example.pagination.domain.models.Image

interface ImageRepository {
     fun getImage(q : String) : Pager<Int, Image>
}