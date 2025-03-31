package com.devted.musinsa.services

import com.devted.musinsa.domain.dto.ProductDto
import com.devted.musinsa.domain.dto.ProductUpdateRequestDto
import com.devted.musinsa.domain.entities.BrandEntity
import com.devted.musinsa.domain.entities.ProductEntity
import com.devted.musinsa.domain.entities.ProductUpdateRequest
import org.springframework.http.ResponseEntity

interface BrandService {
    fun isExistBrand(name: String): Boolean
    fun getProductWithHandleToException(productId: Long):ProductEntity

    // 4번 과제
    fun createBrand(brandEntity: BrandEntity): BrandEntity
    fun addProductInBrand(productEntity: ProductEntity): ProductEntity
    fun fullUpdateProduct(productId: Long, productEntity: ProductEntity): ProductEntity
    fun partialUpdateProduct(productId: Long, productUpdateRequest: ProductUpdateRequest): ProductEntity
}