package org.zerock.w2.listener;

import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@Log4j2
public class W2AppListener implements ServletContextListener {

    @Override //프로젝트 시작
    public void contextInitialized(ServletContextEvent sce){
        log.info("---------init-----------------");
        log.info("---------init-----------------");
        log.info("---------init-----------------");

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("appName","W2");
    }

    @Override //프로젝트 종료
    public void contextDestroyed(ServletContextEvent sce){
        log.info("---------destroy-----------------");
        log.info("---------destroy-----------------");
        log.info("---------destroy-----------------");
    }
}
//ServletContextEvent :
//현재 애플리케이션이 실행되는 공간인 ServletContext에 접근 가능
//ServletContext :
//현재의 웹 애플리케이션 내 모든 자원들을 같이 사용하는 공간, 이 공간에 무언가를 저장하면 모든 컨트롤러나 JSP등에서 이를 활용 할 수 있음
//setAttrubute()통해서 원하는 이름으로 객체 보관가능
