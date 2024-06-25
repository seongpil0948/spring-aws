package com.sixplus.server.api.model;

import com.sixplus.server.api.service.MessageService;
import com.sixplus.server.api.utils.CmmCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.function.Consumer;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<RS> {
	private int status;
	private String  code;
	private String  message;
	private String  sessionId;
	private Object  request;
	private RS      data;
	private Integer dataCnt;

	public Response() { }

	public Response(RS data) {
		this.data = data;
	}

	public static <RS> Response<RS> ok() {
		Response<RS> ret = new Response<>();
		ret.setStatus(HttpStatus.OK.value());
		return ret;
	}
	public static <RS> Response<RS> ok(RS data) {
		Response<RS> ret = new Response<>(data);
		ret.setStatus(HttpStatus.OK.value());
		ret.setCode(CmmCode.SHOP_COMMON_0000.getCode());
		ret.setMessage(MessageService.instance().getMessage(CmmCode.SHOP_COMMON_0000.getMsg(), null));
		return ret;
	}
	public static <RS> Response<RS> ok(RS data, int dataCnt) {
		Response<RS> ret = ok(data);
		ret.setDataCnt(dataCnt);
		return ret;
	}
	
	public static <RS> Response<RS> ok(Object request, RS data, int dataCnt) {
		Response<RS> ret = ok(data);
		ret.setRequest(request);
		ret.setDataCnt(dataCnt);
		return ret;
	}

	public static <RS> Response<RS> ok(String sessionId, RS data) {
		Response<RS> ret = ok(data);
		ret.setSessionId(sessionId);
		return ret;
	}

	public static <RS> Response<RS> ok(String sessionId, RS data, int dataCnt) {
		Response<RS> ret = ok(sessionId, data);
		ret.setDataCnt(dataCnt);
		return ret;
	}

	public static <RS> Response<RS> ok(String sessionId, Object request, RS data) {
		Response<RS> ret = ok(sessionId, data);
		ret.setRequest(request);
		return ret;
	}
	
	public static <RS> Response<RS> ok(String sessionId, Object request, RS data, int dataCnt) {
		Response<RS> ret = ok(sessionId, data);
		ret.setRequest(request);
		ret.setDataCnt(dataCnt);
		return ret;
	}

	public static <RS> Response<RS> accept() {
		Response<RS> ret = new Response<>();
		ret.setStatus(HttpStatus.ACCEPTED.value());
		ret.setCode(CmmCode.SHOP_COMMON_0000.getCode());
		ret.setMessage(MessageService.instance().getMessage(CmmCode.getMessageKeyFromCode(CmmCode.SHOP_COMMON_0000.getCode()).getMsg(), null));
		return ret;
	}

	public static <RS> Response<RS> accept(String code) {
		Response<RS> ret = new Response<>();
		ret.setStatus(HttpStatus.ACCEPTED.value());
		ret.setCode(code);
		ret.setMessage(MessageService.instance().getMessage(CmmCode.getMessageKeyFromCode(code).getMsg(), null));
		return ret;
	}

	public static <RS> Response<RS> accept(String code, String message) {
		Response<RS> ret = accept(code);
		ret.setMessage(message);
		return ret;
	}

	public static <RS> Response<RS> accept(String code, String[] messages) {
		Response<RS> ret = accept(code);
		ret.setMessage(MessageService.instance().getMessage(CmmCode.getMessageKeyFromCode(code).getMsg(), messages));
		return ret;
	}

	/**
	 * Deprecated: Response.accept(...).map(m -> m.setData(any))로 쓰시길...
	 *
	 * @param data
	 * @return Response
	 * @param <RS>
	 */
	@Deprecated
	public static <RS> Response<RS> acceptWithData(RS data) {
		Response<RS> ret = accept();
		ret.setData(data);
		return ret;
	}

	public static <RS> Response<RS> error() {
		Response<RS> ret = new Response<>();
		ret.setStatus(CmmCode.SHOP_EXCEPTION_STATUS_CODE);
		ret.setCode(CmmCode.SHOP_ERROR_9999.getCode());
		ret.setMessage(MessageService.instance().getErrorMessage(CmmCode.SHOP_ERROR_9999.getMsg()));
		return ret;
	}

	public static <RS> Response<RS> error(String code) {
		Response<RS> ret = new Response<>();
		ret.setStatus(CmmCode.SHOP_EXCEPTION_STATUS_CODE);
		ret.setCode(code);
		ret.setMessage(MessageService.instance().getErrorMessage(CmmCode.getMessageKeyFromCode(code).getMsg()));
		return ret;
	}

	public static <RS> Response<RS> error(String code, String message) {
		Response<RS> ret = error(code);
		ret.setMessage(message);
		return ret;
	}

	public static <RS> Response<RS> error(String code, String[] messages) {
		Response<RS> ret = error(code);
		ret.setMessage(MessageService.instance().getMessage(CmmCode.getMessageKeyFromCode(code).getMsg(), messages));
		return ret;
	}

	public static <RS> Response<RS> error(String code, String message, Object request) {
		Response<RS> ret = error(code, message);
		ret.setRequest(request);
		return ret;
	}

	public Response<RS> map(Consumer<Response<RS>> block) {
		block.accept(this);
		return this;
	}
}
