package com.example.jdbcex.controller;

import com.example.jdbcex.dto.TodoDTO;
import com.example.jdbcex.service.TodoService;
import lombok.extern.log4j.Log4j2;

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
