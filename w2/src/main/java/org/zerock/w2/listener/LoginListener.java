package org.zerock.w2.listener;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

//서블릿 리스너 중 HttpSession관련 작업을 감시하는 리스너들을 등록 할 수 있음
// => HttpSessionLisener / HttpSessionAttributeListeer etc
// 이것들을 이용해서 HttpSession이 생성되거나 setAttribute()등의 작업이 이루어질 때 이를 감지 할 수 있음
@Log4j2
public class LoginListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event){

        String name = event.getName();

        Object obj = event.getValue();

        if(name.equals("loginInfo")){
            log.info("A user logined......");
            log.info(obj);
        }
    }

}

//HttpSessionAttributeListener 인터페이스
//attributeAdded(), attributeRemoved(), attributeReplaced() 이용해서
// HttpSession에 setAttribute()/removeAttribute()등의 작업 감지