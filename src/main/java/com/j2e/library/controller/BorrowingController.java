package com.j2e.library.controller;

import com.j2e.library.dto.BorrowingDto;
import com.j2e.library.dto.mapper.BorrowingMapper;
import com.j2e.library.exceptions.*;
import com.j2e.library.service.BorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/borrowing")
@Validated
public class BorrowingController {
    private final BorrowingService borrowingService;


    @GetMapping
    public ResponseEntity<List<BorrowingDto>> getAll() {
        System.out.println("[GET] request find all borrowings");
        return ResponseEntity.ok(borrowingService.getAll());
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<BorrowingDto>> getAllOfUser(@PathVariable Long id) {
        try{
            System.out.println("[GET] request find all borrowings of a user id : "+id);
            return ResponseEntity.ok(borrowingService.getAllOfUser(id));
        }catch (UserNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    @GetMapping("/current/{id}")
    public ResponseEntity<List<BorrowingDto>> getCurrentOfUser(@PathVariable Long id) {
        try{
            System.out.println("[GET] request find all current borrowings of a user id : "+id);
            return ResponseEntity.ok(borrowingService.getCurrentOfUser(id));
        }catch (UserNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    @PostMapping
    public ResponseEntity<String> post(@RequestBody BorrowingDto body) {
        System.out.println("[POST] request create new borrowing");
        try {
            borrowingService.create(BorrowingMapper.toEntity(body));
            System.out.println("[POST] New borrowing created");
            return ResponseEntity.ok("Borrowing created");
        } catch (BorrowingLimitExceededException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (BookUnavailableException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (UserNotFoundException | BookNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> setReturnDate(@RequestBody BorrowingDto body, @PathVariable Long id) {
        try {
            System.out.println("[PATCH] Set return date ("+body.getReturnDate().toString()+") for borrowing id : "+id);
            borrowingService.updateReturnDate(id,body.getReturnDate());
            return ResponseEntity.ok("Return date set");
        } catch (BorrowingReturnDateException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }catch (BookNotFoundException | BorrowingNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            System.out.println("[DELETE] request delete for borrowing id : "+id);
            borrowingService.delete(id);
            return ResponseEntity.ok("Borrowing deleted");
        } catch (BorrowingNotFoundException | BookNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
