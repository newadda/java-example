package org.onecellboy.example;

public class HelloPrint implements IPrint {
    @Override
    public void print() {
        System.out.println("Hello!");
    }
}
