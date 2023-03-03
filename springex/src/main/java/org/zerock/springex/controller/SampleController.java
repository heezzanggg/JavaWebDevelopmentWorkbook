package org.zerock.springex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springex.dto.TodoDTO;

import java.time.LocalDate;

//해당 클래스가 스프링 MVC에서 컨트롤러 역할 한다
@Controller
@Log4j2
public class SampleController {

    //GET방식으로 들어오는 요청 처리
    @GetMapping("/hello")
    public void hello(){
        //void타입은 컨트롤러의 @RequestMapping 값,@GetMapping 등 메소드에서 선언된 값을 그대로 뷰(View)이름으로 사용함
        //문자열타입은 상황에따라 다른 화면을 보여주는 경우에 사용 redirect:(리다이렉트 하는 경우), forward:(브라우저의 URL은 고정하고 내부적으로 다른 URL처리 하는 경우)
        log.info(".........hello");
    }

    @GetMapping("ex1")
    public void ex1(String name, int age){
        log.info("............ex1");
        log.info("name:" + name);
        log.info("age:" + age);
    }

    @GetMapping("ex2")
    public void ex2(@RequestParam(name="name", defaultValue="AAA") String name,
                    @RequestParam(name="age",defaultValue = "20") int age){
        //@RequestParam :간혹 파라미터가전달되지 않았을 시 발생하는 문제를 해결하기위한것. defaultValue 속성 이용하여 파라미터의 기본값 설정
        log.info("............ex2");
        log.info("name:" + name);
        log.info("age:" + age);
    }

    @GetMapping("/ex3")
    public void ex3(LocalDate dueDate){
        log.info("............ex3");
        log.info("dueDate: "+dueDate);
    }

    @GetMapping("/ex4")
    public void ex4(Model model){
        log.info("----------------");
        //이름과 값을 지정해서 뷰(View)에 전달
        model.addAttribute("message","Hello World");
    }

    @GetMapping("/ex4_1")
    public void ex4Extra(@ModelAttribute("dto") TodoDTO todoDTO, Model model){
        //getter, setter를 이용하는 JavaBeans의 형식의 사용자 정의 클래스가 파라미터인 경우,
        //자동으로 화면까지 객체 전달함 => JSP에서 ${todoDTO} 이용 가능
        //자동으로 생성된 변수명 todoDTO 이외의 다른 이름 사용 하고 싶으면 파라미터에 @ModelAttribute("사용할이름") 넣어줌 =>JSP에서 ${dto} 이용 가능
        log.info(todoDTO);
    }

    @GetMapping("/ex5")
    public String ex5(RedirectAttributes redirectAttributes){
        //리다이렉트 할 URL에 쿼리 스트링으로 추가
        redirectAttributes.addAttribute("name","ABC");
        //URL에는 보이지 않지만 JSP에서 일회용으로 사용 가능
        redirectAttributes.addFlashAttribute("result","success");

        return "redirect:/ex6";
    }

    @GetMapping("/ex6")
    public void ex6(){

    }

    @GetMapping("/ex7")
    public void ex7(String p1,int p2){
        log.info("....p1: "+p1);
        log.info("....p2: "+p2);
    }

}
