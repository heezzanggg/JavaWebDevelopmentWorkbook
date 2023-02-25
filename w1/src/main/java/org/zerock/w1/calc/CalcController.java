package org.zerock.w1.calc;

import org.zerock.w1.HelloServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="calcController", urlPatterns = "/calc/makeResult")
//urlPatterns : 브라우저가 호출하는 경로
public class CalcController extends HelloServlet {

    //서블릿은 doPost()라는 메소드를 오버라이드해서 POST방식으로만 들어오는 요청을 처리할 수 있다.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("이쪽으로 넘어와야지??");
        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");
        //req.getParameter()메소드 : 쿼리 스트링으로 전달되는 num1, num2파라미터를 처리하고 있으며 이때 숫자가 아닌 문자열(String)로 처리하고있다.
        //JSP에서는 ${param.num1 }과 같이 단순하게 사용하지만, 서블릿에서는 HttpServletRequest라는 API이용해야만 한다.

        System.out.printf("num1:%s",num1);
        System.out.printf("num2:%s",num2);

        resp.sendRedirect("/index");
        //index.jsp아님
        //url이 index에 해당하는 컨트롤러 없어서 에러발생
    }
}
