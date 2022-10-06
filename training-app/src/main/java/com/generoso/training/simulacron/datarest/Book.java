package com.generoso.training.simulacron.datarest;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Table
public class Book implements Serializable {

    @Id
    private UUID id;

    private String isbn;

    private String title;

    private String publisher;

    @Column
    private Set<String> tags = new HashSet<>();
}