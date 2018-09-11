package org.onecellboy.example;

public class SetterPrint implements IPrint {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void print() {
        System.out.println(this.message);
    }
}
