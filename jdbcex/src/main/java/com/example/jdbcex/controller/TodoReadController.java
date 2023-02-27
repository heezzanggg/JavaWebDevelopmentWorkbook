package com.example.jdbcex.controller;

import com.example.jdbcex.dto.TodoDTO;
import com.example.jdbcex.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="todoReadControlller", value = "/todo/read")
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
            req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req,resp);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("read error");
        }
    }

}
