package com.sillycat.authcenter.web

import com.sillycat.authcenter.model.Book
import com.sillycat.authcenter.service.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun index(): List<Book> = bookService.getAllBooks()

    @Operation(summary = "Post Book Model", description = "Returns 202 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Successful Operation"),
        ]
    )
    @PostMapping
    fun post(@RequestBody book: Book): ResponseEntity<Unit> {
        bookService.createBook(book)
        return ResponseEntity.accepted().build()
    }

    @GetMapping("{id}")
    fun get(@PathVariable id: Int): ResponseEntity<Book> =
        bookService.getBook(id)?.let { ok(it) }
            ?: throw IllegalArgumentException("Book not found")
}