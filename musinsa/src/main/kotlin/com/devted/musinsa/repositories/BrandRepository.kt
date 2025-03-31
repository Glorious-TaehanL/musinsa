package com.devted.musinsa.repositories

import com.devted.musinsa.domain.entities.BrandEntity
import com.devted.musinsa.domain.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<BrandEntity, Long?> {
    fun existsByName(name: String): Boolean
}