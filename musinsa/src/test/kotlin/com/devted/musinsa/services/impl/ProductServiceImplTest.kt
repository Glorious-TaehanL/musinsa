package com.devted.musinsa.services.impl

//test sample data
import com.devted.musinsa.*
import com.devted.musinsa.domain.dto.CategoryDto
import com.devted.musinsa.domain.entities.ProductEntity
import com.devted.musinsa.repositories.BrandRepository
import com.devted.musinsa.repositories.DeleteProductRepository
import com.devted.musinsa.repositories.ProductRepository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceImplTest @Autowired constructor(
    private val underTest: ProductServiceImpl,
    private val productRepository: ProductRepository,
    private val deleteProductRepository: DeleteProductRepository
){
    @BeforeEach
    fun setup() {
        brandRepository.deleteAll()
        productRepository.deleteAll()
    }

    @Autowired
    private lateinit var brandRepository: BrandRepository

    //1번 테스트
    @Test
    fun `test that min price list returns products`(){
        val expectedProducts = listOf(
            testProductWithOutIdEntity(brand = "A", category = "모자", price = 1200),
            testProductWithOutIdEntity(brand = "B", category = "상의", price = 700)
        )

        //상품 업데이트
        val productsToSave = productRepository.saveAll(
            listOf(
                testProductEntityA(),
                testProductEntityAHat(),
                testProductEntityB(),
                testProductEntityBHat(),
                testProductEntityC(),
                testProductEntityCHat()
            )
        )
        assertThat(productsToSave).isNotNull()

        val getMinPriceProducts = underTest.getMinPriceForCategory()

        assertThat(getMinPriceProducts.products)
            .hasSize(2) //사이즈 확인
            .containsExactlyInAnyOrderElementsOf(expectedProducts)
    }

    //2번 과제
    @Test
    fun `test that what brand has total price is minimum sum price`(){
        //상품 업데이트
        val productsToSave = productRepository.saveAll(
            listOf(
                testProductEntityA(),
                testProductEntityAHat(),
                testProductEntityB(),
                testProductEntityBHat(),
                testProductEntityC(),
                testProductEntityCHat()
            )
        )
        assertThat(productsToSave).isNotNull()
        //브랜드 업데이트
        val brandUpdated = brandRepository.saveAll(listOf(
            testBrandEntityA(),
            testBrandEntityB(),
            testBrandEntityC()
        ))
        assertThat(brandUpdated).isNotNull()

        val getMinPriceForBrand = underTest.getMinPriceForBrand()
        //A 합 2000 B합 2400 C합 3000 최저는 A
        assertThat(getMinPriceForBrand.brand).isEqualTo("A")
        assertThat(getMinPriceForBrand.totalPrice).isEqualTo(2000)
    }

    // 3번 과제 테스트 -> 카테고리에 없는 값으로 조회할때
    @Test
    fun `test that get Both price By category return exception when no category name in db`(){
        assertThrows<IllegalArgumentException> {
            underTest.getBothPriceByCategory(CategoryDto(category = "FFFF"))
        }
    }
    // 3번 과제 테스트 -> min , max값 정상출력 테스트
    @Test
    fun `test that both price By category return correct data in db`(){
        val productsToSave = productRepository.saveAll(listOf(testProductEntityB(), testProductEntityC()))
        assertThat(productsToSave).isNotNull()

        val getBothPriceProducts = underTest.getBothPriceByCategory(CategoryDto(category = "상의"))
        val minProduct = testProductWithOutIdCategoryDto(brand = "B", price = 1700)
        val maxProduct = testProductWithOutIdCategoryDto(brand = "Z", price = 2000)

        assertThat(getBothPriceProducts).isNotNull()
        assertThat(getBothPriceProducts.minPrice).isNotEqualTo(minProduct)
        assertThat(getBothPriceProducts.maxPrice).isNotEqualTo(maxProduct)
    }

    //4번 과제 테스트 -> 삭제 테스트.
    @Test
    fun `test that product deleted in th database`() {
        val toBeDeleteProduct = productRepository.save(testProductEntityA())
        assertThat(toBeDeleteProduct.id).isNotNull()

        underTest.deleteProductData(toBeDeleteProduct.id!!)
        // 삭제 확인
        assertThat(productRepository.findById(toBeDeleteProduct.id!!)).isNotNull()
        // 삭제 테이블 등록확인
        val deletedProduct = deleteProductRepository.findByProductId(toBeDeleteProduct.id!!).firstOrNull()
        assertThat(deletedProduct).isNotNull()

        assertThat(deletedProduct?.productId).isEqualTo(toBeDeleteProduct.id)
        assertThat(deletedProduct?.brand).isEqualTo(toBeDeleteProduct.brand)
        assertThat(deletedProduct?.category).isEqualTo(toBeDeleteProduct.category)
        assertThat(deletedProduct?.price).isEqualTo(toBeDeleteProduct.price)
    }
}