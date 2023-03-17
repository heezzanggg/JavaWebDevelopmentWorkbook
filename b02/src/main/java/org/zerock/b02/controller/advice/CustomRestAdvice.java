package org.zerock.b02.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
//@Valid과정에서 문제가 생기면 처리 할 수 있도록 하는 컨트롤러, 어노테이션
//컨트롤러에서 발생하는 예외에 대해 JSON과 같은 순수한 응답 메시지를 생성해서 보낼 수 있음
@Log4j2
public class CustomRestAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String,String>> handleBindException(BindException e){
        //컨트롤러에서 BindException이 던져지는 경우 이를 이용해서 JSON메시지와 400에러(Bad Request)를 전송 함
        log.error(e);

        Map<String,String> errorMap = new HashMap<>();

        if(e.hasErrors()){
            BindingResult bindingResult = e.getBindingResult();

            bindingResult.getFieldErrors().forEach(fieldError ->{
                errorMap.put(fieldError.getField(),fieldError.getCode());
            });
        }

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String,String>> handleFKException(Exception e){
        //DataIntegrityViolationException 발생하면 'constraint fails'메시지를 클라이언트로 전송
        log.error(e);

        Map<String,String> errorMap = new HashMap<>();

        errorMap.put("time",""+System.currentTimeMillis());
        errorMap.put("msg","constraint fails");

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String,String>> handleNoSuchElement(Exception e){

        log.error(e);

        Map<String,String> errorMap = new HashMap<>();

        errorMap.put("time",""+System.currentTimeMillis());
        errorMap.put("msg","No Such Element Exception");

        return ResponseEntity.badRequest().body(errorMap);
    }



}
