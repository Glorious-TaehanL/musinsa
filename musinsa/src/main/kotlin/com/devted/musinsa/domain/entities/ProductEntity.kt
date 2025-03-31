package com.devted.musinsa.domain.entities

import jakarta.persistence.*

@Entity
data class ProductEntity (
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    val id: Long?,

    @Column(name="brand")
    val brand: String,

    @Column(name = "category")
    val category: String,

    @Column(name = "price")
    val price: Int,

)

