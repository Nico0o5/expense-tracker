package com.spinifexit.service;

import java.util.Comparator;
import java.util.List;

import com.spinifexit.model.Expense;
import com.spinifexit.repository.ExpenseRepository;

public class ExpenseManagementService {

    private final ExpenseRepository expenseRepository;

    public ExpenseManagementService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public void viewAll(int choice) {
        sort(choice, expenseRepository.findAll());
    }
    public void find(String keyword) {
        expenseRepository.findByKeyword(keyword).forEach(expense -> System.out.println(expense));;
    }

    public void delete(Long id) {
        expenseRepository.deleteById(id);
    }

    public void create(Expense expense) {
        expenseRepository.save(expense);
    }

    public void sort(int key, List<Expense> expenses) {
        Comparator<Expense> comparator = (expense1, expense2) -> {
            return switch (key) {
                case 1 -> expense1.getCategory().compareTo(expense2.getCategory());
                case 2 -> expense1.getDescription().compareTo(expense2.getDescription());
                case 3 -> expense1.getDate().compareTo(expense2.getDate());
                case 4 -> expense1.getAmount().compareTo(expense2.getAmount());
                default -> -1;
            };
        };
       expenses.stream()
                .sorted(comparator)
                .forEach(expense -> System.out.println(expense));
                
    }
}
