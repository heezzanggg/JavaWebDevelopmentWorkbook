package org.zerock.w2.controller;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.TodoDTO;
import org.zerock.w2.service.TodoService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="TodoListController", value = "/todo/list")
@Log4j2
//목록기능구현
public class TodoListController extends HttpServlet {

    //TodoService 객체를 이용하도록 멤버변수로 TodoService 선언
    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.info("todo list...........");

        ServletContext servletContext = req.getServletContext();
        log.info("appName: "+ servletContext.getAttribute("appName"));
        //HttpServletRequest에는 getServletContext() 메소드 이용해서 ServletContext 이용 가능
        //listener.W2AppListener 부분과 비교
        try {
            List<TodoDTO> dtoList = todoService.listAll();
            req.setAttribute("dtoList",dtoList);
            req.getRequestDispatcher("/WEB-INF/todo/list.jsp").forward(req,resp);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("list error");
        }
    }
}
