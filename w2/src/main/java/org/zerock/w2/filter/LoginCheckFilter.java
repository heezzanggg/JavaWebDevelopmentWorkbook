package org.zerock.w2.filter;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.service.MemberService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/todo/*"})
//특정한 경로를 지정해서 해당 경로의 요청에대해 doFilter()를 실행하는 구조
//브라우저에서 /todo/...로 시작하는 모든경로에 대해서 필터링 시도
@Log4j2
public class LoginCheckFilter implements Filter {
    //세션만 이용
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//        log.info("Login check filter....");
//
//        HttpServletRequest req = (HttpServletRequest)request;
//        HttpServletResponse resp = (HttpServletResponse)response;
//        //doFilter()는 HttpServletRequest,HttpServletResponse보다 상위타입의 파라미터 사용하므로
//        //HTTP와 관련된 작업하려면 다운캐스팅이 필요
//
//        HttpSession session = req.getSession();
//
//        if(session.getAttribute("loginInfo")==null){
//            resp.sendRedirect("/login");
//            return;
//        }
//        chain.doFilter(request,response);
//        //FilterChain의 doFilter() : 다음 필터나 목적지로 갈 수 있도록 함
//    }

    //쿠키와 세션 같이 이용
    @Override
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws ServletException, IOException {
        log.info("Login check filter...");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        if(session.getAttribute("loginInfo")!=null){
            chain.doFilter(request,response);
            return;
        }
        //session에 loginInfo값이 없다면
        //쿠키 체크
        Cookie cookie = findCookie(req.getCookies(),"remember-me");

        //세션에도 없고 쿠키에도 없다면 그냥 로그인
        if(cookie==null){
            resp.sendRedirect("/login");
            return;
        }

        //쿠키 존재하면
        log.info("cookie 존재하는 상황");
        //uuid값
        String uuid = cookie.getValue();

        try{
            //데이터베이스 확인
            MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);

            log.info("쿠키값으로 조회한 사용자 정보:"+memberDTO);
            if(memberDTO==null){
                throw new Exception("Cookie value is not valid");
            }
            //회원정보를 세션에 추가
            session.setAttribute("loginInfo",memberDTO);
            chain.doFilter(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/login");
        }
    }

    private Cookie findCookie(Cookie[] cookies,String name){
        if(cookies==null || cookies.length==0){
            return  null;
        }

        Optional<Cookie> result = Arrays.stream(cookies)
                .filter(ck->ck.getName().equals(name))
                .findFirst();

        return result.isPresent()?result.get():null;
    }
}

