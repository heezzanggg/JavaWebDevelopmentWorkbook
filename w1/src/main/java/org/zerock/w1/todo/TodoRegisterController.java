package org.zerock.w1.todo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//GET방식으로호출되는 경우에는 입력할 수 있는 화면을 보여주고,
//POST방식으로 호출되는 경우에는 등록이 처리된 후 다시 목록페이지를 호출(sendRedirect()).
@WebServlet(name="todoRegisterController", urlPatterns = "/todo/register")
public class TodoRegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        System.out.println("입력화면을 볼 수 있도록 구성");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/todo/register.jsp");
        dispatcher.forward(req,resp);

        //get방식으로 호출되는 경우 등록화면을 보는 구조이므로 RequestDispatcher 이용해서 jsp보도록 작성
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("입력처리하고 목록페이지로 이동");
        //브라우저가 호출해야하는 주소
        resp.sendRedirect("/todo/list");
        //PRG패턴 적용위해 sendRedirect()필요.
        /*POST방식 요청 완료하면 "/todo/list"로 이동*/
    }
}
