package com.devted.musinsa.exception

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException


@RestControllerAdvice
class SimpleExceptionHandler {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(ex: NoSuchElementException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf("code" to 404, "message" to ex.message.orEmpty()))
    }

    @ExceptionHandler(DataIntegrityViolationException ::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(mapOf("code" to 409, "message" to ex.message.orEmpty()))
    }
    // 유효성 검사 실패의 이슈처리
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(mapOf("code" to 400, "message" to ex.message.orEmpty()))
    }

    //@RequestBody Json 요청이 잘못된경우 이슈처리
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<Map<String, Any>> {
        val errorMessage = "Wrong request. please check your request and try again"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(mapOf("code" to 400, "message" to errorMessage))
    }
}