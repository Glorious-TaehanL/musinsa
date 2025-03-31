package com.devted.musinsa.domain.dto

data class ProductDto (
    val id: Long?,
    val brand: String,
    val category: String,
    val price: Int,
)

// 1번 과제 변환
data class ProductWithOutIdDto (
    val brand: String,
    val category: String,
    val price: Int,
)

// 1번 과제-Return DTO
data class ProductGetMinPriceDto (
    val products: List<ProductWithOutIdDto>,
    val totalPrice: Int
)

// 2번 과제 타입변환
data class ProductWithOutIdBrandDto (
    val category: String,
    val price: Int,
)

// 2번 과제-Return DTO
data class BrandProductGetMinPriceDto (
    val brand: String,
    val category: MutableList<List<ProductWithOutIdBrandDto>>,
    val totalPrice: Int,
)

//3번 과제 타입변환
data class ProductWithOutIdCategoryDto (
    val brand: String,
    val price: Int,
)

// 3번 과제-Request DTO
data class CategoryDto(
    val category: String
)

// 3번 과제-Return DTO
data class BothPriceByCategoryDto(
    val category: String,
    val minPrice: ProductWithOutIdCategoryDto,
    val maxPrice: ProductWithOutIdCategoryDto
)
