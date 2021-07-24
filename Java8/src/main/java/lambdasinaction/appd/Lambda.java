package lambdasinaction.appd;

import org.aspectj.lang.annotation.DeclareParents;

import java.util.function.Function;

public class Lambda {

    Function<Object, String> f = Object::toString;
}
