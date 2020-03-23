package test;

import com.sun.media.sound.SoftTuning;
import pojo.Role;

import java.util.Stack;
import java.util.function.Function;

public class Test {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        System.out.println(stack.pop());
        System.out.println(stack.pop());

    }
}
