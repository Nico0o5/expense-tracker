package com.spinifexit;

import com.spinifexit.app.App;
import com.spinifexit.processor.InputKeyboardProcesser;
import com.spinifexit.processor.InputProcessor;
import com.spinifexit.repository.ExpenseListRepository;
import com.spinifexit.repository.ExpenseRepository;
import com.spinifexit.service.ExpenseManagementService;
import com.spinifexit.service.FileManagementService;
import com.spinifexit.service.PerformanceManagementService;

public class Main {
    public static void main(String[] args) {


        ExpenseRepository expenseRepository = new ExpenseListRepository();

        ExpenseManagementService expenseManagementService = new ExpenseManagementService(expenseRepository);
        FileManagementService fileManagementService = new FileManagementService(expenseRepository);
        PerformanceManagementService performanceManagementService = new PerformanceManagementService(expenseRepository);
        InputProcessor inputProcessor = new InputKeyboardProcesser();

        App app = new App(
            expenseManagementService,
            performanceManagementService,
            fileManagementService,
            inputProcessor
        );

        app.initiateApp();



    }
}