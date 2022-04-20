package com.sillycat.authcenter.model

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.Year

@Schema(description = "Model for a Book")
data class Book(
    val id: Int,
    @field:Schema(description = "Book model", example = "apache", type = "string")
    val model: String,
    @field:Schema(description = "Book maker", example = "pdf", type = "string")
    val make: String,
    @field:Schema(
        description = "A year when this book was publish",
        example = "2021",
        type = "int",
        minimum = "1800",
        maximum = "2500"
    )
    val year: Year,
    @field:Schema(
        description = "Book price",
        type = "number",
        format = "float",
        minimum = "0.0",
        example = "59.34")
    val price: BigDecimal
)
