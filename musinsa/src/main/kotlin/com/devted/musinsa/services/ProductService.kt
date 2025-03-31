package com.devted.musinsa.services

import com.devted.musinsa.domain.dto.*
import com.devted.musinsa.domain.entities.ProductEntity
import org.springframework.http.ResponseEntity

interface ProductService {
    // 1번 과제
    fun getMinPriceForCategory(): ProductGetMinPriceDto

    // 2번 과제
    fun getMinPriceForBrand(): BrandProductGetMinPriceDto

    // 3번 과제
    fun getBothPriceByCategory(categoryDto: CategoryDto): BothPriceByCategoryDto

    fun createProduct(productEntity: ProductEntity): ProductEntity
    fun deleteProductData(productId: Long)
}