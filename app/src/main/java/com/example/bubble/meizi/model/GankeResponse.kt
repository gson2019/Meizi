package com.example.bubble.meizi.model

data class GankeResponse(
    val data: List<Girl>,
    val page: Int,
    val page_count: Int,
    val status: Int,
    val total_count: Int
)