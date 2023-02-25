package org.zerock.w1.calc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="sampleServlet", urlPatterns = "/sample")
//urlPatterns : 브라우저가 호출하는 경로
public class SampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet..."+this );}

    @Override
    public void destroy(){
        System.out.println("destroy.....");
    }

    @Override
    public void init(ServletConfig config)throws ServletException{
        System.out.println("init(ServletConfig)........(sampleServlet 시작이지)");
    }

}

// SampleServelt경로에 해당하는 '/sample' 호출하면 init()메소드, doGet()메소드 실행 됨
// 톰캣 종료하면 destroy()호출됨
// init(), destroy()는 한번씩만 호출 됨
// doGet(), doPost()는 여러번 호출 됨
// HttpServletRequest: HTTP메시지 형태로 들어오는 요청(Request)에 대한 정보를 파악하기위해 제공됨
