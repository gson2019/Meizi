package com.example.bubble.meizi.model

data class PixabayPhotoResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)