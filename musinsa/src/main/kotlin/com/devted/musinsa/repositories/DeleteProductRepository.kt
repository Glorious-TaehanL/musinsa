package com.devted.musinsa.repositories

import com.devted.musinsa.domain.entities.DeletedProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DeleteProductRepository: JpaRepository<DeletedProductEntity, Long?> {
    fun findByProductId(productId: Long): MutableList<DeletedProductEntity>
}