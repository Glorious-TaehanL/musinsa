package com.devted.musinsa.domain.entities

data class ProductUpdateRequest (
    val id: Long? = null,
    val brand: String? = null,
    val category: String? = null,
    val price: Int? = null,
)