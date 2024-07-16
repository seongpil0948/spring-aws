package com.sixplus.server.api.core.annotation;


import com.sixplus.server.api.core.validator.ShopRequiredValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ShopRequiredValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ShopRequired {
    String message() default "필수값이 없습니다.";
    String field() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
