package com.spinifexit.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.spinifexit.model.Expense;
import com.spinifexit.processor.InputProcessor;
import com.spinifexit.service.ExpenseManagementService;
import com.spinifexit.service.FileManagementService;
import com.spinifexit.service.PerformanceManagementService;

public class App {

    private final ExpenseManagementService expenseManagementService;
    private final PerformanceManagementService performanceManagementService;
    private final FileManagementService fileManagementService;
    private final InputProcessor inputProcessor;


    public App(ExpenseManagementService expenseManagementService,
        PerformanceManagementService performanceManagementService,
        FileManagementService fileManagementService,
        InputProcessor inputProcessor){
            this.expenseManagementService = expenseManagementService;
            this.performanceManagementService = performanceManagementService;
            this.fileManagementService = fileManagementService;
            this.inputProcessor = inputProcessor;
        }


    public void initiateApp() {

        ExecutorService fileWriterThread = Executors.newSingleThreadExecutor();
        fileManagementService.read();
        while(true){
            if(!continueProcess())
                break;
        }
        // I'd argue that using concurrency is not the best for this project, but I'll just put it here to make use of multi-threading 
        fileWriterThread.execute(() -> fileManagementService.write());
        fileWriterThread.shutdown(); 
    }

    private boolean continueProcess() {

        outputMenu();

        String input = inputProcessor.getInput();
        if(input.isEmpty())
            return false;

        int choice;

        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid input (Follow the instruction)");
            return true;
        }
        
        switch (choice) {
            case 1:
                show(); break;
            case 2:
                search();
                break;
            case 3:
                add(); break;
            case 4:
                delete();
                break;
            case 5:
                report();
                System.out.println("Report generated sucessfully");
                break;
            case 6: 
                return false;
            default:
                System.out.println("Error: Invalid input (Follow the instruction)");
                break;
        }
        return true;
    }

    private void report() {
        performanceManagementService.generate();
    }

    private void show() {
        outputSortKey();
        int choice;
        while(true){
                
            String input = inputProcessor.getInput();
            if(input.isEmpty())
                continue;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid input (Follow the instruction)");
                continue;
            }
            if(choice > 4 || choice < 1){
                System.err.println("Error: Invalid input (Follow the instruction)");
                continue;
            }
            break;
        }
        expenseManagementService.viewAll(choice);
    }

    private void search() {
        System.out.print("Search for: ");
        String input = inputProcessor.getInput();
        if(input == null)
            input = "";
        expenseManagementService.find(input);
    }
   
    private void add() {
        Expense expense = new Expense();

        boolean isValidValue = false;
        while (!isValidValue) {
            System.out.print("Expense Description: ");
            String description = inputProcessor.getInput();
            isValidValue = expense.isValidThenSetDescription(description);
        }

        isValidValue = false;
        while (!isValidValue) {
            System.out.print("Expense Category: ");
            String category = inputProcessor.getInput();
            isValidValue = expense.isValidThenSetCategory(category);
        }

        isValidValue = false;
        while (!isValidValue) {
            System.out.print("Expense Date (YYYY-MM-DD): ");
            String date = inputProcessor.getInput();
            isValidValue = expense.isValidThenSetDate(date);
        }

        isValidValue = false;
        while (!isValidValue) {
            System.out.print("Expense Amount: ");
            String amount = inputProcessor.getInput();
            isValidValue = expense.isValidThenSetAmount(amount);
        }

        expenseManagementService.create(expense);

    }

    private void delete() {
        System.out.print("Input the Expense ID: ");
        String input = inputProcessor.getInput();
        Long id;
        try {
            id = Long.parseLong(input);
            expenseManagementService.delete(id);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid input (Follow the instruction)");
            return;
        }
    }

    private void outputSortKey() {
        String output = """


                ===========================================
                Choose field to sort (Type only the number)
                ===========================================
                1 : CATEGORY
                2 : DESCRIPTION
                3 : DATE
                4 : AMOUNT
                ========================================
                """;
        System.out.println(output);
    }

    private void outputMenu() {
        String output = """


                ========================================
                  Choose action  (Type only the number)
                ========================================
                1 : SHOW MY EXPENSES
                2 : SEARCH
                3 : ADD NEW EXPENSE
                4 : DELETE EXPENSE
                5 : GENERATE REPORT
                6 : EXIT
                ========================================
                """;
        System.out.println(output);
    }
}
