package joshie.harvest.core.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HFLoader {
    /** This is the priority,
     *  If the number is higher,
     *  this will be sorted higher**/
    int priority() default 1;
}
