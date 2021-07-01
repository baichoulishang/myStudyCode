package io.github.viscent.mtia.ch2;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @date 2021/6/22 21:10
 */

@Component
@Aspect
public class RoleAspect {

    @Pointcut("execution(* RoleService.printRole(..))")
    public void print() {
    }


    @Before("print()")
    public void before() {
        System.out.println("前置方法");
    }

    @After("execution(* RoleService.printRole(..))")
    public void after() {
        System.out.println("后置方法");
    }

    @AfterReturning("execution(* RoleService.printRole(..))")
    public void afterReturning() {
        System.out.println("正常执行完的方法");
    }

    @AfterThrowing("execution(* RoleService.printRole(..))")
    public void afterThrowing() {
        System.out.println("异常了的方法");
    }
}

