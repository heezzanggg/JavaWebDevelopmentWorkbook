package org.zerock.w1;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="myServlet", urlPatterns = "/my")
//@WebServlet : 브라우저의 경로와 해당 서블릿을 연결하는 설정을 위해 사용
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //doGet() : 브라우저의 주소를 직접 변경해서 접근하는 경우에 호출되는 메소드
        resp.setContentType("text/html");

        //PrintWriter : 서블릿에서 PrintWriter객체 이용해 브라우저쪽으로 출력 처리, 브라우저로 무언가를 출력하기위한 용도
        PrintWriter out =resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>MyServlet</h1>");
        out.println("</body></html>");

    }
}
