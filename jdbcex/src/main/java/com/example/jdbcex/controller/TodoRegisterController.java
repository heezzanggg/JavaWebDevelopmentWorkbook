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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name="todoRegisterController",value = "/todo/register")
@Log4j2
//등록기능구현
//=> get 방식으로 등록 화면 보고
//=> <form>태그 내에 입력한 항목들을 채운 후에 post 방식으로 처리
//=> 처리후 목록화면으로 redirect 하는 PRG(post-redirect-get)패턴 방식
public class TodoRegisterController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;
    private final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("/todo/register GET.....");
        req.getRequestDispatcher("/WEB-INF/todo/register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        TodoDTO todoDTO =TodoDTO.builder()
                .title(req.getParameter("title"))
                .dueDate(LocalDate.parse(req.getParameter("dueDate"),DATEFORMATTER))
                .build();

        log.info("/todo/register POST.....");
        log.info(todoDTO);
        try {
            todoService.register(todoDTO); //서비스의 register()메소드에서 dto -> vo 변환 후 dao에서 데이터 insert함
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/todo/list");
    }
}
