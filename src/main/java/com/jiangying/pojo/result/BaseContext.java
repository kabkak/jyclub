package com.jiangying.pojo.result;

public class BaseContext {

   // public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static InheritableThreadLocal<Long> threadLocal = new InheritableThreadLocal<>();

    public static void setCurrentId(Long id) {

        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}