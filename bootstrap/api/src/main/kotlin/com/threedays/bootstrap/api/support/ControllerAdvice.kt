package com.threedays.bootstrap.api.support

import com.threedays.oas.model.ErrorResponse
import com.threedays.support.common.base.exception.CustomException
import com.threedays.support.common.exception.NotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.OffsetDateTime

@RestControllerAdvice
class ControllerAdvice {

    companion object {

        private val logger = KotlinLogging.logger {}

    }


    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.error(e) { "IllegalArgumentException" }

        val response: ErrorResponse = createErrorResponse("COMMON", "1001")

        return createResponseEntity(
            status = HttpStatus.BAD_REQUEST,
            response = response
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ErrorResponse> {
        logger.error(e) { "NotFoundException" }

        val response: ErrorResponse = createErrorResponse("COMMON", "1002")

        return createResponseEntity(HttpStatus.NOT_FOUND, response)
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        logger.error(e) { "CustomException" }

        val response: ErrorResponse = createErrorResponse(e.type, e.code)

        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, response)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<ErrorResponse> {
        logger.error(e) { "RuntimeException" }

        val response: ErrorResponse = createErrorResponse("COMMON", "1003")

        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, response)
    }

    private fun createErrorResponse(
        type: String,
        code: String
    ): ErrorResponse {
        return ErrorResponse(
            time = OffsetDateTime.now(),
            type = type,
            code = code
        )
    }

    private fun createResponseEntity(
        status: HttpStatus,
        response: ErrorResponse
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(status).body(response)
    }

}
