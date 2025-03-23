package com.spinifexit.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputKeyboardProcesser implements InputProcessor {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String getInput() {
        try {
            return reader.readLine();
        } catch (IOException e) {
           System.err.println("IO Exception: Please check your input or output interface.");
           return ""; 
        }
    }
}
