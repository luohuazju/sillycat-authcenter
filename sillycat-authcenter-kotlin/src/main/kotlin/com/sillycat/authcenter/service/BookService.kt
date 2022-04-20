package com.sillycat.authcenter.service

import com.sillycat.authcenter.model.Book
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Service
class BookService {

    private val books: ConcurrentHashMap<Int, Book> = ConcurrentHashMap()
    private val size = AtomicInteger(0)

    fun createBook(book: Book): Book = Book(size.getAndIncrement(), book.model, book.make, book.year, -BigDecimal.ONE).also {
        books[it.id] = it
    }

    fun getBook(id: Int): Book? = books[id]

    fun getAllBooks(): List<Book> = books.values.toList()

    fun setPrice(id: Int, price: BigDecimal) {
        books[id] = books[id]?.copy(price = price) ?: throw IllegalArgumentException("Not found")
    }
}