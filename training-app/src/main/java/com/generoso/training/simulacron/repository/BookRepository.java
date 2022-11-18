package com.generoso.training.simulacron.repository;

import com.generoso.training.simulacron.model.Book;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface BookRepository extends CassandraRepository<Book, String> {
}
