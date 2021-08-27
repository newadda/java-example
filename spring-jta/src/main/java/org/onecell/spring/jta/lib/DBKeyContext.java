package org.onecell.spring.jta.lib;

public class DBKeyContext<T> {
    private final ThreadLocal<T> CONTEXT = new ThreadLocal<>();


    public void setDbKey(T  dbKey) {
        CONTEXT.set(dbKey);
    }

    public  T get() {
        return CONTEXT.get();
    }

    public  void clear() {
        CONTEXT.remove();
    }
}
