package com.devted.musinsa.initializer

import com.devted.musinsa.domain.entities.BrandEntity
import com.devted.musinsa.domain.entities.ProductEntity
import com.devted.musinsa.repositories.BrandRepository
import com.devted.musinsa.repositories.ProductRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.io.File

@Configuration
@Profile("!test")
class Initializer{
    @Bean
    fun initializeDb(
        productRepository: ProductRepository,
        brandRepository: BrandRepository
    ) = ApplicationRunner {
        if (productRepository.count() == 0L) { //check for insert seed data
            loadJsonProducts().forEach { productRepository.save(it) }
        }
        if(brandRepository.count() == 0L) {
            loadJsonBrands().forEach { brandRepository.save(it) }
        }
    }

    private fun loadJsonProducts(): List<ProductEntity> {
        val file = File("src/main/resources/seedData/product-data.json")
        if (file.exists()) {
            return jacksonObjectMapper().readValue(file)
        } else {
            return emptyList()
        }
    }

    private fun loadJsonBrands(): List<BrandEntity> {
        val file = File("src/main/resources/seedData/brand-data.json")
        if (file.exists()) {
            return jacksonObjectMapper().readValue(file)
        } else {
            return emptyList()
        }
    }

}