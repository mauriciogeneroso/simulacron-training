package com.generoso.training.simulacron.datarest;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Data
@Table
public class Book implements Serializable {

    @Id
    private String id;

    private String isbn;

    private String title;

    private String publisher;
}