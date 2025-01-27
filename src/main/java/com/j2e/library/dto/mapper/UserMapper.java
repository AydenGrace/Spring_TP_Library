package com.j2e.library.dto.mapper;

import com.j2e.library.dto.UserDto;
import com.j2e.library.entity.User;

import java.util.ArrayList;

public class UserMapper {
    public static UserDto toDto(User user){
        return new UserDto(
                user.getName(),
                user.getEmail()
        );
    }
    public static User toEntity(UserDto dto){
        return new User(
                null,
                dto.getName(),
                dto.getEmail(),
                new ArrayList<>()
        );
    }

}
