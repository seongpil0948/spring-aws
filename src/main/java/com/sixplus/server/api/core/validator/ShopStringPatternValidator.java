package com.sixplus.server.api.core.validator;

import com.sixplus.server.api.core.annotation.ShopStringPattern;
import com.sixplus.server.api.utils.CmmUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ShopStringPatternValidator implements ConstraintValidator<ShopStringPattern, String> {
    private String field;
    private String[] patterns;

    private String message;

    @Override
    public void initialize(ShopStringPattern shopDefault) {

        this.field = shopDefault.field();
        if(StringUtils.isNotEmpty(shopDefault.pattern())) {
            this.patterns = new String[]{shopDefault.pattern()};
        } else if(shopDefault.patterns().length > 0) {
            this.patterns = shopDefault.patterns();
        }
        this.message = shopDefault.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        if(StringUtils.isNotEmpty(value)) {
            if(this.patterns.length > 0 && Arrays.stream(this.patterns).noneMatch(value.trim()::contains)) {
                if(StringUtils.isNotEmpty(this.field)) {
                    this.addConstraintViolation(cxt, CmmUtils.append(this.field, " ", this.message));
                }
                return false;
            }
        }

        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String msg) {
        //기본 메시지 비활성화
        context.disableDefaultConstraintViolation();
        //새로운 메시지 추가
        context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
    }
}
