package com.j2e.library.dto.mapper;

import com.j2e.library.dto.BorrowingDto;
import com.j2e.library.entity.Borrowing;

public class BorrowingMapper {
    public static BorrowingDto toDto(Borrowing borrow){
        return new BorrowingDto(
                UserMapper.toDto(borrow.getUser()),
                BookMapper.toDto(borrow.getBook()),
                borrow.getBorrowDate(),
                borrow.getReturnDate()
        );
    }

    public static Borrowing toEntity(BorrowingDto dto){
        return new Borrowing(
                null,
                UserMapper.toEntity(dto.getUser()),
                BookMapper.toEntity(dto.getBook()),
                dto.getBorrowDate(),
                dto.getReturnDate()
        );
    }
}
