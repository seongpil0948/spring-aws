package com.sixplus.server.api.core.annotation;


import com.sixplus.server.api.core.validator.ShopStringPatternValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ShopStringPatternValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ShopStringPattern {
    String field() default "";
    String pattern() default "";
    String[] patterns() default {};
    String message() default "형식이 맞지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
