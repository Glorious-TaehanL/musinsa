package com.devted.musinsa.domain.entities

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
data class DeletedProductEntity (
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name="productId")
    val productId: Long,

    @Column(name="brand")
    val brand: String,

    @Column(name="category")
    val category: String,

    @Column(name="price")
    val price: Int,

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = null,

    )