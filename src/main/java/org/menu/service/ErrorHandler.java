package org.menu.service;

public class ErrorHandler {
    public static String errorMassage(String str, Exception e) {
        return "Error in " + str + ": " + e.getMessage();
    }
}
