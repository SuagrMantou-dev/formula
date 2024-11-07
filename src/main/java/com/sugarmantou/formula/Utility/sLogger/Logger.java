package com.sugarmantou.formula.Utility.sLogger;

public class Logger {

    public static void log(String text) {
        if (text == null) {
            text = "";
        }
        System.out.println(text);
    }

    public static void log(String... texts) {
        if (texts == null || texts.length == 0) {
            System.out.println();
            return;
        }
        for (String text : texts) {
            System.out.println(text != null ? text : "");
        }
    }

    public static void err(String text) {
        if (text == null) {
            text = ""; // If null, set to empty string
        }
        System.err.println(text);
    }

    public static void err(String... texts) {
        if (texts == null || texts.length == 0) {
            System.out.println();
            return;
        }
        for (String text : texts) {
            System.out.println(text != null ? text : "");
        }
    }
}
