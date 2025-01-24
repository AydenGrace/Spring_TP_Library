package com.j2e.library.controller;

import com.j2e.library.entity.User;
import com.j2e.library.exceptions.UserEmailAlreadyExistException;
import com.j2e.library.exceptions.UserNotFoundException;
import com.j2e.library.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        System.out.println("[GET] request find all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        System.out.println("[GET] request find user by id : "+id);
        try{
            return ResponseEntity.ok(userService.getById(id));
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found",e);
        }
    }
    @PostMapping
    public ResponseEntity<String> post(@Valid @RequestBody User body){
        System.out.println("[POST] request create new user");
        try{
            userService.createUser(body);
            System.out.println("[POST] New user created");
            return ResponseEntity.ok("User created");
        }catch (UserEmailAlreadyExistException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> patch(@PathVariable Long id,@Valid @RequestBody User body){
        System.out.println("[PATCH] request modify user id : "+id);
        try{
            userService.updateUser(id,body);
            System.out.println("[PATCH] user modified");
            return ResponseEntity.ok("User modified");
        }catch (UserEmailAlreadyExistException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }catch (UserNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        System.out.println("[DELETE] request delete user id : "+id);
        try{
            userService.deleteUser(id);
            System.out.println("[DELETE] user deleted");
            return ResponseEntity.ok("User deleted");
        }catch (UserNotFoundException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
