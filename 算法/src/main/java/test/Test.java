package test;

import pojo.Role;

import java.util.function.Function;

public class Test {
    public static void main(String[] args) {
        Function<Integer, Role> function = Role::new;
        Role apply = function.apply(100);
    }
}
