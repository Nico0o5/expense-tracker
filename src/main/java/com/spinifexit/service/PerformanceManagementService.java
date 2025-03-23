package com.spinifexit.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.spinifexit.repository.ExpenseRepository;

public class PerformanceManagementService {

    ExpenseRepository expenseRepository;

    public PerformanceManagementService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public void generate() {
        Random random = new Random();
        String path = "report_" + LocalDate.now().toString() + "_" + random.nextInt(1, 30) + random.nextInt(1, 10) + random.nextInt(1, 20) + ".txt";
        File file = new File(path);
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

            bw.write(getReport());
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }

    public String getReport() {
        
        Map<String, BigDecimal> mp =  expenseRepository.findAll().stream()
        .collect(Collectors.groupingBy((expense) -> expense.getCategory(), 
                                            Collectors.collectingAndThen(
                                                Collectors.summingDouble(expense -> expense.getAmount().doubleValue()), expense -> BigDecimal.valueOf(expense))));
        
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        String result = "============ Report =========== \n";
        for (Map.Entry<String, BigDecimal> entry : mp.entrySet()) {
            String currency = formatter.format(entry.getValue());
            String value = " Category : %S -> Total : %s \n";
            result = result.concat(String.format(value, entry.getKey(), currency));
        }
        return result; 
        
    }

}
