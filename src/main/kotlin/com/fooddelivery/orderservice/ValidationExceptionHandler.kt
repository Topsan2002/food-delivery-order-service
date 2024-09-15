package com.fooddelivery.orderservice

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.function.Consumer


@ControllerAdvice
class ValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun notValid(ex: MethodArgumentNotValidException, request: HttpServletRequest?): ResponseEntity<*> {
        val errors: MutableList<String?> = ArrayList()

        ex.allErrors.forEach(Consumer { err: ObjectError -> errors.add(err.defaultMessage) })

        val result: MutableMap<String, List<String?>> = HashMap()
        result["errors"] = errors

        return ResponseEntity<Map<String, List<String?>>>(result, HttpStatus.BAD_REQUEST)
    }
}