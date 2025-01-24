package com.j2e.library.service;

import com.j2e.library.entity.User;
import com.j2e.library.exceptions.UserEmailAlreadyExistException;
import com.j2e.library.exceptions.UserNotFoundException;
import com.j2e.library.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final IUserRepository userRepository;

    //CREATE
    public void createUser(User user) throws UserEmailAlreadyExistException{
        //Verify email
        userRepository.findByEmail(user.getEmail()).ifPresentOrElse(userFound -> {
            throw new UserEmailAlreadyExistException();
        }, () -> userRepository.save(user));
    }

    //READ
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public User getByName(String name) throws UserNotFoundException {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public User getByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    //UPDATE
    public void updateUser(Long id, User newUser) throws UserEmailAlreadyExistException, UserNotFoundException {
        userRepository.findById(id).ifPresentOrElse(user -> {
                    if (newUser.getEmail() != null && !newUser.getEmail().equals(user.getEmail())) {
                        //update email
                        //Verify already exist
                        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
                            throw new UserEmailAlreadyExistException();
                        } else {
                            user.setEmail(newUser.getEmail());
                        }
                    }
                    if (newUser.getName() != null && !newUser.getName().equals(user.getName())) {
                        user.setName(newUser.getName());
                    }
                    userRepository.save(user);
                },
                () -> {
                    //User not found
                    throw new UserNotFoundException();
                });
    }

    //DELETE
    public void deleteUser(Long id) throws UserNotFoundException {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete, () -> {
            //User not found
            throw new UserNotFoundException();
        });
    }
}
