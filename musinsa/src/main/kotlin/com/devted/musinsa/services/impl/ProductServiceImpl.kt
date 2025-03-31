package com.devted.musinsa.services.impl

import com.devted.musinsa.domain.dto.*
import com.devted.musinsa.domain.entities.DeletedProductEntity
import com.devted.musinsa.domain.entities.ProductEntity
import com.devted.musinsa.repositories.BrandRepository
import com.devted.musinsa.repositories.DeleteProductRepository
import com.devted.musinsa.repositories.ProductRepository
import com.devted.musinsa.services.ProductService
import com.devted.musinsa.services.toProductWithOutIdBrandDto
import com.devted.musinsa.services.toProductWithOutIdCategoryDto
import com.devted.musinsa.services.toProductWithOutIdDto
import jakarta.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val deleteProductRepository: DeleteProductRepository
) : ProductService {

    //1번과제
    override fun getMinPriceForCategory(): ProductGetMinPriceDto {
        val minPriceProducts = productRepository.findMinPrice();
        if (minPriceProducts.isEmpty()) {
            throw NoSuchElementException("MIN PRICE PRODOCUTS NOT FOUND");
        }
        val totalPrice = minPriceProducts.sumOf { it.price }
        if (totalPrice == 0) {
            throw NoSuchElementException("PRODUCTS HAVE NOT MIN PRICE");
        }
        //리턴값 타입케스팅 Extenstions.kt 정의
        return ProductGetMinPriceDto(
            products = minPriceProducts.map { it.toProductWithOutIdDto() },
            totalPrice = totalPrice
        )

    }

    // 2번 과제
    override fun getMinPriceForBrand(): BrandProductGetMinPriceDto {
        val brandObj = brandRepository.findAll()
        val brands = brandObj.mapNotNull { it.name }
        if (brands.isEmpty()) {
            throw NoSuchElementException("BRANDS HAVE NOT FOUND");
        }
        var minBrand = ""
        var minPrice = Int.MAX_VALUE
        val lowPriceBrandData: MutableList<List<ProductWithOutIdBrandDto>> = mutableListOf()

        for (brand in brands) {
            // 구해진 브랜드별로 각카테고리의 합을 구하고
            // 비교이후에 최저값인 브랜드만 lowPriceBrandData에 데이터보관.
            val brandData = productRepository.findProductEntityByBrand(brand)
            val sumPrice = brandData.sumOf { it.price }

            if (minPrice > sumPrice && sumPrice != 0) {
                minBrand = brand
                minPrice = sumPrice
                //리턴값 타입케스팅 Extenstions.kt 정의
                val branOBJ = brandData.map { it.toProductWithOutIdBrandDto() }
                //기존에 저장된 최저값브랜드 데이터 삭제
                lowPriceBrandData.clear()
                lowPriceBrandData.add(branOBJ)
            }
        }
        // 리턴값 타입케스팅
        return BrandProductGetMinPriceDto(
            brand = minBrand,
            category = lowPriceBrandData,
            totalPrice = minPrice
        )
    }

    // 3번 과제
    override fun getBothPriceByCategory(categoryDto: CategoryDto): BothPriceByCategoryDto {
        val categories = productRepository.getCategories()
        val categoryName = categoryDto.category

        //매개변수가 카테고리에 포함되지않을때,
        if(!categories.contains(categoryName)){
            throw IllegalArgumentException("$categoryName is not found in categories")
        }

        //return [0] = 최저가격 // [1] = 최대가격
        val bothBrandData = productRepository.getBothPriceByCategory(categoryName)
        if(bothBrandData.isEmpty()) {
            throw NoSuchElementException("$categoryName 's PRODUCT HAVE NOT MIN PRICE OR MAX PRICE");
        }
        //리턴값 타입케스팅 Extenstions.kt 정의
        return BothPriceByCategoryDto(
            category = categoryName,
            minPrice = bothBrandData[0].toProductWithOutIdCategoryDto(),
            maxPrice = bothBrandData[1].toProductWithOutIdCategoryDto()
        )
    }

    // 4번 과제
    @Transactional
    override fun createProduct(productEntity: ProductEntity): ProductEntity {
        val alreadyProduct = (productRepository.findByBrandAndCategoryAndPrice(
            brand = productEntity.brand,
            category = productEntity.category,
            price = productEntity.price)
        )
        if (alreadyProduct != null) {
            throw DataIntegrityViolationException("Same information has product is already exists")
        }
        return productRepository.save(productEntity)
    }

    //4번과제
    @Transactional
    override fun deleteProductData(productId: Long) {
        val targetProduct = productRepository.findByIdOrNull(productId)
        if (targetProduct == null) { //존재하지않을때
            throw DataIntegrityViolationException("Product with ID $productId not found")
        }
        //추적을 위한 삭제테이블
        deleteProductRepository.save(DeletedProductEntity(
            productId = productId,
            category = targetProduct.category,
            brand = targetProduct.brand,
            price = targetProduct.price,
            createdAt = LocalDateTime.now()
        ))
        productRepository.deleteById(productId)
    }



}