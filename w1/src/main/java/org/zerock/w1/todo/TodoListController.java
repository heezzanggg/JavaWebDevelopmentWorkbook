package org.zerock.w1.todo;

import org.zerock.w1.todo.dto.TodoDTO;
import org.zerock.w1.todo.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="todolistController",urlPatterns = "/todo/list")
//브라우저 호출 주소 urlPatterns
public class TodoListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req , HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("/todo/list");

        List<TodoDTO> dtoList = TodoService.INSTANCE.getList();

        req.setAttribute("list",dtoList);
        //'list'라는 이름으로 List<TodoDTO>객체를 보관,
        // setAttribute()를 이용해서 보관된 데이터는 JSP에서 EL로 간단하게 확인 할 수 있음

        req.getRequestDispatcher("/WEB-INF/todo/list.jsp")
                .forward(req,resp);
        //HttpServletRequest 의 getRequestDispatcher()메소드 : 현재의 요청을 다른 서버의 자원(서블릿 or jsp)에게 전달하는 용도
    }
}
