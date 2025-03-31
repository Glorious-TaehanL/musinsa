package com.devted.musinsa.domain.entities

import jakarta.persistence.*

@Entity
data class BrandEntity(
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

//    @Column(unique = true,name="name")
    @Column(name="name")
    val name: String
)