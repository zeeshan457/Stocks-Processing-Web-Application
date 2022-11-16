package com.example.app.Data.Entity;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.Type;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

// Annotations for this class
@MappedSuperclass
@Getter
@Setter
public class AbstractEntity {
    @Id
    @Unique
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
