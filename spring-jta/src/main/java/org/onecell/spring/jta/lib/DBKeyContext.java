package org.onecell.spring.jta.lib;

public class DBKeyContext {
    private final ThreadLocal<String> CONTEXT = new ThreadLocal<>();


    public void setDbKey(String  dbKey) {
        CONTEXT.set(dbKey);
    }

    public  String get() {
        return CONTEXT.get();
    }

    public  void clear() {
        CONTEXT.remove();
    }
}
