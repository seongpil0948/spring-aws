package com.sixplus.server.api.core.exception;


import com.sixplus.server.api.service.MessageService;
import com.sixplus.server.api.utils.CmmCode;

public class ShopErrorException extends RuntimeException {
    private String code;
    private String message;

    public ShopErrorException() {
        super(MessageService.instance().getErrorMessage(CmmCode.SHOP_ERROR_9999.getMsg()));
        this.code = CmmCode.SHOP_ERROR_9999.getCode();
    }
    public ShopErrorException(String code) {
        super(MessageService.instance().getErrorMessage(CmmCode.getMessageKeyFromCode(code).getMsg()));
        this.message = MessageService.instance().getErrorMessage(CmmCode.getMessageKeyFromCode(code).getMsg());
        this.code = code;
    }

    public ShopErrorException(String code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public ShopErrorException(String code, String[] messages) {
        super(MessageService.instance().getErrorMessage(CmmCode.getMessageKeyFromCode(code).getMsg(), messages));
        this.message = MessageService.instance().getErrorMessage(CmmCode.getMessageKeyFromCode(code).getMsg(), messages);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() { return this.message; }
}
