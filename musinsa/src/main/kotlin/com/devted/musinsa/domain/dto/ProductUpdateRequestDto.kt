package com.devted.musinsa.domain.dto

data class ProductUpdateRequestDto (
    val id: Long?,
    val brand: String?,
    val category: String?,
    val price: Int?,
)