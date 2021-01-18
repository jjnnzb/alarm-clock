package com.github.powerjobdemo.aop;

import com.alibaba.fastjson.JSONObject;
import com.github.powerjobdemo.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Global Exception Handler
 *
 * @author Jjn
 * @since 2021-01-18 16:30
 */
@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Response defaultErrorHandler(HttpServletRequest req, Exception e) {
        String requestUrl = req.getRequestURI();
        JSONObject errorObject = new JSONObject();
        errorObject.put("errorLocation", e.toString());
        errorObject.put("requestUri", requestUrl);
        return Response.error(errorObject);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleBindException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        JSONObject errorObject = new JSONObject();
        errorObject.put("errorLocation", ex.toString());
        assert fieldError != null;
        return Response.error(fieldError.getDefaultMessage(), errorObject);
    }

    @ExceptionHandler(BindException.class)
    public Response handleBindException(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        JSONObject errorObject = new JSONObject();
        errorObject.put("errorLocation", ex.toString());
        assert fieldError != null;
        return Response.error(fieldError.getDefaultMessage(), errorObject);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response httpRequestMethodHandler() {
        return Response.error(500,"Request method not supported", new JSONObject());
    }
}
