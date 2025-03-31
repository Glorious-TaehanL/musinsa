package com.devted.musinsa.domain.dto

data class BrandDto(
    val id: Long?,
    val name: String
)

data class CreateBrandDto(
    val brand: String
)
