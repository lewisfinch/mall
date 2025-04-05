package com.mall.utils;

public class UserContext {

    private static final ThreadLocal<Integer> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(Integer userId) {
        currentUser.set(userId);
    }

    public static Integer getCurrentUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
