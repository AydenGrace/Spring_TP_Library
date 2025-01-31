package com.j2e.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User name cannot be null")
    @NotBlank(message = "User name cannot be blank")
    @Column(nullable = false)
    private String name;

    @Email(regexp = ".+[@].+[\\.].+",message = "User Email not valid")
    @NotNull(message = "User Email cannot be null")
    @NotBlank(message = "User Email cannot be blank")
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Borrowing> borrowings;
}
