package com.devted.musinsa.controller

import com.devted.musinsa.domain.dto.*
import com.devted.musinsa.services.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/api/v1/product")
class ProductController(
    private val productService: ProductService
) {
    //API HEALTH CHECK.
    @GetMapping("/health")
    fun healthCheck():String{
        return "API Health Check";
    }

    // 1번 과제
    @GetMapping("/getMinPriceByCategories")
    fun getMinPriceForCategory(): ProductGetMinPriceDto {
        return productService.getMinPriceForCategory()
    }

    // 2번 과제
    @GetMapping("/getMinPriceByBrand")
    fun getMinPriceForBrand(): BrandProductGetMinPriceDto {
        return productService.getMinPriceForBrand()
    }

    // 3번 과제
    @PostMapping("/getBothPriceByCategory")
    fun getBothPriceByCategory(@RequestBody categoryDto: CategoryDto): BothPriceByCategoryDto {
        return productService.getBothPriceByCategory(categoryDto);
    }

}