package com.devted.musinsa

import com.devted.musinsa.domain.dto.*
import com.devted.musinsa.domain.entities.BrandEntity
import com.devted.musinsa.domain.entities.DeletedProductEntity
import com.devted.musinsa.domain.entities.ProductEntity
import com.devted.musinsa.domain.entities.ProductUpdateRequest
import java.time.LocalDateTime

fun testProductEntity(id: Long? = null,brand: String,category:String, price: Int) = ProductEntity(
    id = id,
    brand = brand,
    category = category,
    price = price
)
fun testProductUpdateEntity(
    id: Long? = null,
    brand: String? = null,
    category:String? = null,
    price: Int? = null) = ProductUpdateRequest(
    id = id,
    brand = brand,
    category = category,
    price = price
)
fun testProductWithOutIdEntity(brand: String,category:String, price: Int) = ProductWithOutIdDto(
    brand = brand,
    category = category,
    price = price
)

//-- brand Entity --//
fun testBrandEntityA(id: Long? = null) = BrandEntity(
    id = id,
    name = "A",
)
fun testBrandEntityB(id: Long? = null) = BrandEntity(
    id = id,
    name = "B",
)
fun testBrandEntityC(id: Long? = null) = BrandEntity(
    id = id,
    name = "C",
)

//-- Product Entity --//
fun testProductEntityA(id: Long? = null) = testProductEntity(
    id = id,
    brand = "A",
    category = "상의",
    price = 800
)
fun testProductEntityAHat(id: Long? = null) = testProductEntity(
    id = id,
    brand = "A",
    category = "모자",
    price = 1200
)

fun testProductEntityB(id: Long? = null) = testProductEntity(
    id = id,
    brand = "B",
    category = "상의",
    price = 700
)
fun testProductEntityBHat(id: Long? = null) = testProductEntity(
    id = id,
    brand = "B",
    category = "모자",
    price = 1700
)

fun testProductEntityC(id: Long? = null) = testProductEntity(
    id = id,
    brand = "C",
    category = "상의",
    price = 1000
)

fun testProductEntityCHat(id: Long? = null) = testProductEntity(
    id = id,
    brand = "C",
    category = "모자",
    price = 2000
)

fun testProductWithOutIdCategoryDto(brand: String, price: Int) = ProductWithOutIdCategoryDto(
    brand = brand,
    price = price
)

fun testDeletedProductEntity(
    id: Long? = null,
    productId: Long,
    brand: String,
    category: String,
    price: Int
) = DeletedProductEntity(
    id = id,
    productId = productId,
    brand = brand,
    category = category,
    price = price,
)