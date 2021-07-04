package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @date 2020/2/29 9:28
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCase {
    // 2020年02月29日 09:28:36,自定义的注解
    public int id();

    public String description() default "已完成";
    // public String description() default "已完成";

}
