package com.devted.musinsa.services

import com.devted.musinsa.domain.dto.*
import com.devted.musinsa.domain.entities.BrandEntity
import com.devted.musinsa.domain.entities.ProductEntity
import com.devted.musinsa.domain.entities.ProductUpdateRequest

fun ProductEntity.toProductDto() = ProductDto(
    id = this.id,
    brand = this.brand,
    price = this.price,
    category = this.category
)

fun ProductDto.toProductEntity() = ProductEntity(
    id = null,
    brand = this.brand,
    price = this.price,
    category = this.category
)

fun ProductEntity.toProductWithOutIdDto() = ProductWithOutIdDto(
    brand = this.brand,
    price = this.price,
    category = this.category
)

fun ProductEntity.toProductWithOutIdBrandDto() = ProductWithOutIdBrandDto(
    category = this.category,
    price = this.price
)

fun ProductEntity.toProductWithOutIdCategoryDto() = ProductWithOutIdCategoryDto(
    brand = this.brand,
    price = this.price
)

fun ProductUpdateRequestDto.toProductUpdateRequest() = ProductUpdateRequest(
    id = this.id,
    brand = this.brand,
    category = this.category,
    price = this.price
)
fun BrandEntity.toBrandDto() = BrandDto(
    id = this.id,
    name = this.name
)

fun CreateBrandDto.toBrandEntity() = BrandEntity(
    name = this.brand
)