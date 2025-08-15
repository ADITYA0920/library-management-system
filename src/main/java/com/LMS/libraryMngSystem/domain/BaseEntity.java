package com.LMS.libraryMngSystem.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "created", nullable = false)
    Date created = new Date();

    @Column(name = "created_by", nullable = false)
    String createdBy;

    @Column(name = "last_modified", nullable = false)
    Date lastModified = new Date();

    @Column(name = "last_modified_by", nullable = false)
    String lastModifiedBy;
}
