package com.spinifexit.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.spinifexit.model.Expense;

public class ExpenseListRepository implements ExpenseRepository {

    private List<Expense> expenses = new ArrayList<>();

    // Fake data
    /* public ExpenseListRepository(){
        expenses = new ArrayList<>(Arrays.asList(
            new Expense(1l, "Buy Egg, Colgate", "Groceries", "2025-21-03", BigDecimal.valueOf(500)),
            new Expense(2l, "Buy Hamburger", "Food", "2025-21-03", BigDecimal.valueOf(50)),
            new Expense(3l, "Beep Card Load", "Transportaion", "2025-21-03", BigDecimal.valueOf(300))
            ));
    } 
    */
    

    @Override
    public List<Expense> findAll() {
        return expenses;
    }

    @Override
    public Optional<Integer> findById(Long id) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId().equals(id)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Expense> findByKeyword(String keyword) {

        String regex = ".*" + keyword + ".*";

        Predicate<Expense> matcher = expense -> {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(expense.toString());
            return m.matches();
        };

        List<Expense> result = expenses.stream()
                                       .filter(matcher)
                                       .toList();
        return result;
    }


    public synchronized void save(Expense expense) {
        expense.setId(expenses.size() + 1l);
        expenses.add(expense);
        
    }

    public synchronized void deleteById(Long id) {
        Optional<Integer> result = findById(id);
        if(result.isPresent()){
            expenses.remove(result.get().intValue());
            System.out.println("Record deleted sucessfully");
        }  
        else
            System.err.println("Error: Expense not found");
    }

}
