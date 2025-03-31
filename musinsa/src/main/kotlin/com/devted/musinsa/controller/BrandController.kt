package com.devted.musinsa.controller

import com.devted.musinsa.domain.dto.*
import com.devted.musinsa.repositories.ProductRepository
import com.devted.musinsa.services.*
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/api/v1/brand")
class BrandController(
    private val brandService: BrandService,
    private val productRepository: ProductRepository,
    private val productService: ProductService
) {
    // 4번 과제 ( 브랜드 추가 )
    @PostMapping("/create")
    fun setBrand(@Valid @RequestBody createBrandDto: CreateBrandDto): BrandDto {
        return brandService.createBrand(createBrandDto.toBrandEntity()).toBrandDto()
    }

    // 4번 과제 ( 상품 추가 )
    @PostMapping("/addProduct")
    fun addProduct(@Valid @RequestBody productDto: ProductDto): ProductDto {
         return brandService.addProductInBrand(
             productDto.toProductEntity()
         ).toProductDto()
    }

    //4번 과제 ( 상품 전체 업데이트 )
    @PutMapping(path = ["/editProduct/{productId}"])
    fun editProduct(@PathVariable("productId") productId: Long,@RequestBody productDto: ProductDto): ProductDto {
         return brandService.fullUpdateProduct(
             productId, productDto.toProductEntity()
         ).toProductDto()
    }

    //4번 과제 ( 상품 일부 업데이트 )
    @PatchMapping(path = ["/editProduct/{productId}"])
    fun updateProduct(@PathVariable("productId") productId: Long,@RequestBody productUpdateRequestDto: ProductUpdateRequestDto): ProductDto {
        return brandService.partialUpdateProduct(
            productId, productUpdateRequestDto.toProductUpdateRequest()
        ).toProductDto()
    }

    //4번 과제 ( 상품 제거 )
    @DeleteMapping(path = ["/deleteProduct/{productId}"])
    fun deleteProduct(@PathVariable("productId") productId: Long) : ResponseEntity<Any> {
        productService.deleteProductData(productId)
        return ResponseEntity.ok(mapOf("code" to 204,"message" to "Product is successfully deleted"))
    }

}