package com.LMS.libraryMngSystem.domain.user.entity;

import com.LMS.libraryMngSystem.domain.BaseEntity;
import com.LMS.libraryMngSystem.domain.book.entity.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    public enum Role {
        READER,
        ADMIN
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.READER;

    @Override
    public String toString() {
        return getId() + "-" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Book)) return false;
        User other = (User) o;
        return this.email.equals(other.getEmail());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
