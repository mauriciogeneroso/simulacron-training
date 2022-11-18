package com.generoso.training.simulacron.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    @Id
    private String id;

    private String isbn;

    private String title;

    private String publisher;

    public Book(String id) {
        this.id = id;
    }
}