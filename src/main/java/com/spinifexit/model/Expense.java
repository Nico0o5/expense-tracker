package com.spinifexit.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expense {

    private Long id;
    private String description;
    private String category;
    private String date;
    private BigDecimal amount;

    public Expense(){

    }

    public Expense(Long id, String description, String category, String date, BigDecimal amount){
        this.id = id;
        this.description = description;
        this.category = category;
        this.date = date;
        this.amount = amount;
    }

    public Expense(String description, String category, BigDecimal amount){

        this.description = description;
        this.category = category;
        this.date = new Date().toString();
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public boolean isValidThenSetDate(String date) {
        Pattern p = Pattern.compile("(2[0-9]{3})\\-(([0-2][1-9])|(3[0-1])|([1-3]0))\\-((0[1-9])|(1[0-2]))");
        Matcher m = p.matcher(date);
        if(m.matches()){
            this.date = date;
            return true;
        }
        System.err.println("Error: Invalid date assignment");
        return false;
    }

    public boolean isValidThenSetAmount(String amount) {
        Pattern p = Pattern.compile("[0-9\\.]+");
        Matcher m = p.matcher(amount);
        if(m.matches()){
            this.amount = BigDecimal.valueOf(Double.parseDouble(amount));
            return true;
        }
        System.err.println("Error: Invalid amount assignment");
        return false;
    }

    public boolean isValidThenSetCategory(String category) {
        if(category == null){
            System.err.println("Error: Can't take empty category");
            return false;
        }
        category = category.replace(',', ' ');
        if(category.isBlank()){
            System.err.println("Error: Can't take empty description");
            return false;
        }
        this.category = category;
        return true;
    }

    public boolean isValidThenSetDescription(String description) {
        
        if(description == null){
            System.err.println("Error: Can't take empty description");
            return false;
        }
        description = description.replace(',', ' ');
        if(description.isBlank()){
            System.err.println("Error: Can't take empty description");
            return false;
        }
        this.description = description;
        return true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        String currency = formatter.format(this.amount);
        String format = "%d [%s] %s - %s - ₱%s";
        return String.format(format, this.id, this.category, this.description, this.date, currency);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Expense){
            Expense expense = (Expense) obj;
            return this.id.equals(expense.getId());
        }

        return false;     
    }
    


}
