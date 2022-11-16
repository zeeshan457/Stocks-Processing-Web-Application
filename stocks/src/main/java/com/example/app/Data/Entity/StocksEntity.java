package com.example.app.Data.Entity;

import com.vaadin.flow.component.crud.Crud;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Annotations for this class using Lombok
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "stocks")
public class StocksEntity extends AbstractEntity {

    @Column(nullable = false, length = 50)
    private String Symbol;

    @Column(nullable = false, length = 255)
    private String Information;



}
