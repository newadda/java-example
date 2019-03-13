package org.onecellboy.common.validator;

public class Token {
    public enum TOKEN_TYPE{
        BASIC,
        ARRAY;
    }
    private String name;
    public Token(String name)
    {
        this.name =name;
    }
    public String getName()
    {
        return this.name;
    }

}
