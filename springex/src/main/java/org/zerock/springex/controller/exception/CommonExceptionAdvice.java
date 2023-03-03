package org.zerock.springex.controller.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

//컨트롤러에서 발생하는 예외 처리하는 기능 제공
@ControllerAdvice
@Log4j2
public class CommonExceptionAdvice {

    //REST방식에서 사용, 만들어진 문자열,JSON데이터를 그대로 브라우저에 전송하는 방식
    @ResponseBody
    //@ExceptionHandler를 가진 모든 메소드는 해당타입의 예외를 파라미터로 전달 받을 수 있음
    @ExceptionHandler(NumberFormatException.class)
    public String exceptNumber(NumberFormatException numberFormatException){

        log.error("------------------------------");
        log.error("numberFormatException.getMessage()");
        return "NUMBER FORMAT EXCEPTION";

    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String exceptCommon(Exception exception){
        log.error("-------------------");
        log.error(exception.getMessage());

        StringBuffer buffer = new StringBuffer("<ul>");
        buffer.append("<li>" + exception.getMessage()+"</li>");
        Arrays.stream(exception.getStackTrace())
                .forEach(stackTraceElement -> {buffer.append("<li>"+stackTraceElement+"</li>");
        });
        buffer.append("</ul");

        return buffer.toString();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(){
        return "custom404";
    }
}
