package com.ebooks.database.repos;

import com.ebooks.database.entity.OperationsOnBooks;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OperationsOnBooksRepos extends CrudRepository<OperationsOnBooks, Integer> {

    List<OperationsOnBooks> findOperationsOnBooksByBookId(Integer bookId);

    OperationsOnBooks save(OperationsOnBooks operationsOnBooks);
}
