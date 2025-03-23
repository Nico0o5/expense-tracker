package com.spinifexit.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.spinifexit.model.Expense;
import com.spinifexit.repository.ExpenseRepository;

public class FileManagementService {

    private ExpenseRepository expenseRepository;

    public FileManagementService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public void write() {
        File file = new File("expenses.csv");
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            if(file.length() == 0){
                bw.write("ID,DESCRIPTION,CATEGORY,DATE,AMOUNT");
                bw.newLine();
            }

            for (Expense expense : expenseRepository.findAll()) {
                bw.write(expense.getId() + ",");
                bw.write(expense.getDescription() + ",");
                bw.write(expense.getCategory() + ",");
                bw.write(expense.getDate() + ",");
                bw.write(expense.getAmount().toString());
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        File file = new File("expenses.csv");
        if(!file.exists()){
            System.err.println("File not found.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;

            System.out.println("Processing Credential. . .");

            while( (line = br.readLine()) != null ){

                if(isHeader == true){
                    isHeader = false;
                    continue;
                }
                
                String[] result = line.split(",");

                if(result.length != 5) // Validation when the CSV is tampered
                    continue;

                

                long id = Long.parseLong(result[0]);
                String description = result[1];
                String category = result[2];
                String date = result[3];
                BigDecimal amount = BigDecimal.valueOf(Float.parseFloat(result[4])).setScale(2, RoundingMode.HALF_UP);
    

                Expense expense = new Expense( id, description, category, date,  amount);
                expenseRepository.save(expense);

            }
        } catch (IOException e) {
            System.err.println("Error: A problem occurs when processing the file.");
        }
    }

}
