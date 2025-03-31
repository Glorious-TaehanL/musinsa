package com.devted.musinsa.services.impl

import com.devted.musinsa.domain.dto.ProductDto
import com.devted.musinsa.domain.entities.BrandEntity
import com.devted.musinsa.domain.entities.ProductEntity
import com.devted.musinsa.domain.entities.ProductUpdateRequest
import com.devted.musinsa.repositories.BrandRepository
import com.devted.musinsa.repositories.ProductRepository
import com.devted.musinsa.services.BrandService
import com.devted.musinsa.services.ProductService
import jakarta.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BrandServiceImpl(
    private val brandRepository: BrandRepository,
    private val productService: ProductService,
    private val productRepository: ProductRepository
) : BrandService {

    override fun isExistBrand(name: String): Boolean {
        return brandRepository.existsByName(name)
    }

    override fun getProductWithHandleToException(productId: Long):ProductEntity {
        val existingProduct = productRepository.findByIdOrNull(productId)
        if(existingProduct == null){
            throw NoSuchElementException("Product with id $productId does not exists")
        }
        return existingProduct
    }

    // 4번 과제 (브랜드생성)
    override fun createBrand(brandEntity: BrandEntity): BrandEntity {
        if(isExistBrand(brandEntity.name)){
            throw DataIntegrityViolationException("${brandEntity.name} is already exists")
        }
        return brandRepository.save(brandEntity)
    }

    // 4번 과제 (브랜드에 상품추가)
    override fun addProductInBrand(productEntity: ProductEntity): ProductEntity {
        if(!isExistBrand(productEntity.brand)) { //브랜드가 존재하지않을때는 브랜드도 추가.
            createBrand(BrandEntity(name = productEntity.brand))
        }
        return productService.createProduct(productEntity)
    }

    // 4번 과제 (상품 전체변경 및 새로운 브랜드 추가 후 업데이트)
    @Transactional
    override fun fullUpdateProduct(productId: Long, productEntity: ProductEntity): ProductEntity{
        val brandName = productEntity.brand
        val existingProduct = getProductWithHandleToException(productId) //id가 없는경우 exception발생. id로 찾아질땐 productEntity 리턴

        if(!isExistBrand(brandName)){ //존재하지않는 브랜드의 경우
            createBrand(BrandEntity(name = brandName))//브랜드 생성
        }
        val updatedProduct = productEntity.copy(id= existingProduct.id)
        return productRepository.save(updatedProduct)
    }

    // 4번 과제 (상품 일부변경 및 새로운 브랜드 추가 후 업데이트)
    @Transactional
    override fun partialUpdateProduct(productId: Long, productUpdateRequest: ProductUpdateRequest): ProductEntity {
        val brandName = productUpdateRequest.brand;
        val existingProduct = getProductWithHandleToException(productId) //id가 없는경우 exception발생. id로 찾아질땐 productEntity 리턴

        if(brandName != null){  //브랜드에 값이 들어왔고, 같은브랜드의 존재여부 확인
            if(!isExistBrand(brandName)){ //존재하지않는 브랜드의 경우
                createBrand(BrandEntity(name = brandName)) //브랜드 생성
            }
        }

        val updatedProduct = existingProduct.copy(
            brand = productUpdateRequest.brand ?: existingProduct.brand,
            category = productUpdateRequest.category ?: existingProduct.category,
            price = productUpdateRequest.price ?: existingProduct.price
        )
        return productRepository.save(updatedProduct)
    }



}