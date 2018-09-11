package org.onecellboy.example;

public class ConstructorPrint implements IPrint {
    private String message;

    public ConstructorPrint(String message)
    {
        this.message = message;
    }


    @Override
    public void print() {
        System.out.println(this.message);
    }
}
