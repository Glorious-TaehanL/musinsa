package com.devted.musinsa.repositories

import com.devted.musinsa.domain.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductEntity,Long?> {

    // 1번 과제 사용쿼리
    @Query("""
        SELECT pe
        FROM ProductEntity pe
        WHERE pe.price = (
            SELECT MIN(price)
            FROM ProductEntity 
            WHERE category = pe.category
        )
        ORDER BY pe.category
    """)
    fun findMinPrice(): List<ProductEntity>

    // 2번 과제 사용쿼리
    fun findProductEntityByBrand(brand: String): List<ProductEntity>

    // 3번 과제
    @Query("""
        SELECT pe
        FROM ProductEntity pe
        JOIN (SELECT
                    MIN(target.price) as minPrice,
                    MAX(target.price) as maxPrice 
                FROM ProductEntity target
                WHERE target.category = :categoryName 
        ) ON pe.price in (minPrice, maxPrice)
        """)
    fun getBothPriceByCategory(@Param("categoryName") categoryName: String): List<ProductEntity>
    @Query("SELECT DISTINCT category FROM ProductEntity")
    fun getCategories(): List<String>
//    fun getDistinctByCategory(): List<ProductEntity>

    //4번 과제
    fun findByBrandAndCategoryAndPrice(@Param("brand") brand: String, @Param("category") category: String, @Param("price") price: Int): ProductEntity?

}