package com.j2e.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Book title cannot be null")
    @NotBlank(message = "Book title cannot be blank")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Book author cannot be null")
    @NotBlank(message = "Book author cannot be blank")
    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private boolean isAvailable = true;

    @OneToMany(mappedBy = "book",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Borrowing> borrowings;


}
