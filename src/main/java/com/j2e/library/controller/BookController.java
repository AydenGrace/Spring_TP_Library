package com.j2e.library.controller;

import com.j2e.library.dto.BookDto;
import com.j2e.library.dto.mapper.BookMapper;
import com.j2e.library.entity.Book;
import com.j2e.library.exceptions.BookNotFoundException;
import com.j2e.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
@Validated
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll(){
        System.out.println("[GET] request find all books");
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/available/{available}")
    public ResponseEntity<List<Book>> getAllAvailable(@PathVariable boolean available){
        System.out.println("[GET] request find all books with available "+available);
        return ResponseEntity.ok(bookService.getByIsAvailable(available));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable Long id){
        System.out.println("[GET] request find book by id : "+id);
        try{
            return ResponseEntity.ok(bookService.getById(id));
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found",e);
        }
    }

    @PostMapping
    public ResponseEntity<String> postBook(@RequestBody @Valid BookDto body){
        System.out.println("[POST] request create new book");
        try{
            bookService.createBook(BookMapper.toEntity(body));
            System.out.println("[POST] New book created");
            return ResponseEntity.ok("Book created");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patch(@PathVariable Long id, @RequestBody BookDto body){
        System.out.println("[PATCH] request modify book id : "+id);
        try{
            bookService.update(id,BookMapper.toEntity(body));
            System.out.println("[PATCH] book modified");
            return ResponseEntity.ok("Book modified");
        }catch (BookNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/toggle/{id}")
    public ResponseEntity<String> toggleAvailable(@PathVariable Long id){
        System.out.println("[PATCH] request toggle available book id : "+id);
        try{
            bookService.toggleAvailable(id);
            System.out.println("[PATCH] book toggled");
            return ResponseEntity.ok("Book toggled");
        }catch (BookNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        System.out.println("[DELETE] request delete book id : "+id);
        try{
            bookService.delete(id);
            System.out.println("[DELETE] book deleted");
            return ResponseEntity.ok("Book deleted");
        }catch (BookNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
