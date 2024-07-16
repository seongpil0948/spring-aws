package com.sixplus.server.api.core.validator;

import com.sixplus.server.api.core.annotation.ShopRequired;
import com.sixplus.server.api.service.MessageService;
import com.sixplus.server.api.utils.CmmCode;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShopRequiredValidator implements ConstraintValidator<ShopRequired, Object> {
    private String field;

    @Override
    public void initialize(ShopRequired shopRequired) {
        this.field = shopRequired.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cxt) {
        if(ObjectUtils.isEmpty(value)) {
            if(StringUtils.hasText(this.field)) {
                this.addConstraintViolation(cxt, MessageService.instance().getErrorMessage(CmmCode.getMessageKeyFromCode("5001").getMsg(), new String[]{ this.field }));
            } else {
                this.addConstraintViolation(cxt, MessageService.instance().getErrorMessage(CmmCode.getMessageKeyFromCode("5000").getMsg()));
            }
            return false;
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
