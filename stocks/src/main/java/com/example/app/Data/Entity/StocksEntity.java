package com.example.app.Data.Entity;

import com.vaadin.flow.component.crud.Crud;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "stocks")

    /**
     *
     * Creating table for stocks
     * Column 1 is symbol
     * Column 2 is information
     *
     */
public class StocksEntity extends AbstractEntity {

    @Column(nullable = false, length = 50)
    private String Symbol;

    @Column(nullable = false, length = 255)
    private String Information;



}
