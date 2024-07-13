package com.sixplus.server.api.service;

import com.sixplus.server.api.utils.CmmUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {
	private final MessageSource messageSource;
	
	private static MessageService instance;
	
	@PostConstruct
	private void init() {
		MessageService.instance = this;
	}
	
	public MessageSource getValidatorMessageSource() {
		return messageSource;
	}

	public static MessageService instance() {
		return MessageService.instance;
	}
	
	public String getValidatorMessage(String code, Object[] args) {
		return getMessage(messageSource, "", code, args);
		
	}
	
	public String getValidatorMessage(String code) {
		return getValidatorMessage(code, null);
	}
	
	public String getErrorMessage(String code, Object[] args) {
		return getMessage(messageSource, "", code, args);
	}
	
	public String getErrorMessage(String code) {
		return getErrorMessage(code, null);
	}
	
	public String getMessage(String code, Object[] args) {
        return getMessage(messageSource, "", code, args);
    }
	
	private String getMessage(MessageSource src, String cat, String code, Object[] args) {
		try {
			String ret = src.getMessage(cat.concat(code), args, LocaleContextHolder.getLocale());
			return CmmUtils.defaultIfEmpty(ret, code);
		} catch (Exception e) {
			log.warn("message source : {}{}[{}]", cat, code, e.getMessage());
			return code;
		}
	}
	
}
