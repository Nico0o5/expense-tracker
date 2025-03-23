package com.spinifexit.repository;

import java.util.List;
import java.util.Optional;

import com.spinifexit.model.Expense;

public interface ExpenseRepository {

    public List<Expense> findAll();
    public List<Expense> findByKeyword(String keyword);
    public void save(Expense expense);
    public Optional<Integer> findById(Long id);
    public void deleteById(Long id);

}
