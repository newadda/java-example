package org.onecell;

public class Test01Dto {
    String a;
    String b;

    Test02Dto sub;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public Test02Dto getSub() {
        return sub;
    }

    public void setSub(Test02Dto sub) {
        this.sub = sub;
    }
}
