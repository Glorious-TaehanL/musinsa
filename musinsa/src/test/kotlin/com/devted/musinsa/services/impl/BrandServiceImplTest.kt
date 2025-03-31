package com.devted.musinsa.services.impl

import com.devted.musinsa.repositories.BrandRepository
import com.devted.musinsa.repositories.ProductRepository
import com.devted.musinsa.testBrandEntityA
import com.devted.musinsa.testProductEntity
import org.assertj.core.api.Assertions.*
import com.devted.musinsa.testProductEntityA
import com.devted.musinsa.testProductUpdateEntity
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class BrandServiceImplTest @Autowired constructor(
    private val underTest: BrandServiceImpl,
    private val brandRepository: BrandRepository,
    private var productRepository: ProductRepository

){
    @BeforeEach
    fun setup() {
        // 테스트 전 초기화 작업
        brandRepository.deleteAll()
        productRepository.deleteAll()
    }

    //4번 과제 테스트 -> 브랜드를 추가하고 디비에 정상등록되는지
    @Test
    fun `test that create brand in th database`() {
        val savedBrand = underTest.createBrand(testBrandEntityA())
        assertThat(savedBrand.id).isNotNull()

        val recalledBrand = brandRepository.findById(savedBrand.id!!)
        assertThat(recalledBrand).isNotNull()
        assertThat(recalledBrand.get()).isEqualTo(
            testBrandEntityA(id=savedBrand.id)
        )
    }

    //4번 과제 테스트 -> 등록한 상품이 정상등록되었는지,
    @Test
    fun `test that add product In brand in th database`(){
        val addedProduct = underTest.addProductInBrand(testProductEntityA())
        assertThat(addedProduct.id).isNotNull()

        val recalledProduct = productRepository.findById(addedProduct.id!!)
        assertThat(recalledProduct).isNotNull()

        assertThat(recalledProduct.get()).isEqualTo(
            testProductEntityA(id=addedProduct.id)
        )
    }

    //4번 과제 테스트 -> 등록한 상품의 일부 수정.
    @Test
    fun `test that partitial update product in th database`() {
        val updatedData = testProductUpdateEntity(
            category = "아우터",
            price = 850
        )
        val addedProduct = underTest.addProductInBrand(testProductEntityA())
        assertThat(addedProduct.id).isNotNull()
        val expectedProduct = testProductEntity(
            id = addedProduct.id,
            brand = "A",
            category = "아우터",
            price = 850
        )

        val updatedProduct = underTest.partialUpdateProduct(addedProduct.id!!, updatedData)
        assertThat(updatedProduct).isEqualTo(expectedProduct)
    }

    //4번 과제 테스트 -> 등록한 상품의 전체 수정.
    @Test
    fun `test that full update product in th database`() {
        val updatedData = testProductEntity(
            brand = "B",
            category = "모자",
            price = 1800
        )
        val addedProduct = underTest.addProductInBrand(testProductEntityA())
        assertThat(addedProduct.id).isNotNull()
        val expectedProduct = testProductEntity(
            id = addedProduct.id,
            brand = "B",
            category = "모자",
            price = 1800
        )
        val updatedProduct = underTest.fullUpdateProduct(addedProduct.id!!, updatedData)
        assertThat(updatedProduct).isEqualTo(expectedProduct)
    }

}