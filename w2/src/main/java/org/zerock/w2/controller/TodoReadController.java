package org.zerock.w2.controller;


import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.TodoDTO;
import org.zerock.w2.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="todoReadController", value = "/todo/read")
@Log4j2
//조회기능
//=> get 방식으로 동작, 파라미터를 쿼리 스트링으로 번호를 전달하는 방식
public class TodoReadController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        try {
            Long tno = Long.parseLong(req.getParameter("tno"));
            TodoDTO todoDTO = todoService.get(tno);
            //데이터담기
            req.setAttribute("dto",todoDTO);
            //쿠키찾기 (쿠키이름: viewTodos)
            Cookie viewTodoCookie = findCookie(req.getCookies(),"viewTodos");
            String todoListStr = viewTodoCookie.getValue();
            boolean exist = false;

            if(todoListStr!=null &&todoListStr.indexOf(tno+"-") >=0){
                exist=true;
            }

            log.info("exist"+exist);

            if(!exist){
                todoListStr += tno+'-';
                viewTodoCookie.setValue(todoListStr);
                viewTodoCookie.setMaxAge(60 * 60*24);
                viewTodoCookie.setPath("/");
                resp.addCookie(viewTodoCookie);
            }
            req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req,resp);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new ServletException("read error");
        }
    }

    private Cookie findCookie(Cookie[] cookies,String cookiName){

        Cookie targetCookie = null;

        if(cookies !=null &&cookies.length>0){
            for(Cookie ck:cookies){
                if(ck.getName().equals(cookiName)){
                    targetCookie=ck;
                    break;
                }
            }
        }
        if(targetCookie ==null){
            targetCookie = new Cookie(cookiName,"");
            targetCookie.setPath("/");
            targetCookie.setMaxAge(60*60*24);
        }
        return targetCookie;
    }

}
