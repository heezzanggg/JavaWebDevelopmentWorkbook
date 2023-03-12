package org.zerock.springex.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.springex.dto.PageRequestDTO;
import org.zerock.springex.dto.TodoDTO;
import org.zerock.springex.service.TodoService;

import javax.validation.Valid;

@Controller
@RequestMapping("/todo")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

//    @RequestMapping("/list") //=>최종경로 : /todo/list
//    public void list(){
//        log.info(".................todo list");
//    }

//    @RequestMapping(value = "/register" , method = RequestMethod.GET)
//    public void registerGet(){
//        log.info(".......GET todo register");
//    }

//    @PostMapping("/register")
//    public void registerPost(TodoDTO todoDTO){
//        log.info("........POST todo register");
//        log.info(todoDTO);
//    }

    @GetMapping("/register")
    public void registerGET(){
        log.info("GET todo register");
    }

    @PostMapping("/register")
    public String registerPOST(@Valid TodoDTO todoDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info("POST todo register.....");

        if(bindingResult.hasErrors()){
            log.info(".........................has error");
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            return "redirect:/todo/register";
        }
        log.info(todoDTO);

        todoService.register(todoDTO);

        return "redirect:/todo/list";
    }

//    @RequestMapping("/list")
//    public void List(Model model){
//
//        log.info("todo list........");
//        model.addAttribute("dtoList",todoService.getAll());
//    }


    @GetMapping("/list")
    public void list(@Valid PageRequestDTO pageRequestDTO, BindingResult bindingResult, Model model){

        System.out.println("시작이다!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info(pageRequestDTO);

        if(bindingResult.hasErrors()){
            pageRequestDTO = PageRequestDTO.builder().build();
        }
        System.out.println(pageRequestDTO);
        System.out.println("-----------------------------------------------------------------");
        System.out.println(todoService.getList(pageRequestDTO));
        model.addAttribute("responseDTO", todoService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long tno,Model model,PageRequestDTO pageRequestDTO){

        log.info("todo read........");

        TodoDTO todoDTO = todoService.getOne(tno);
        log.info(todoDTO);

        model.addAttribute("dto",todoDTO);
    }

    @PostMapping("/remove")
    public String remove(Long tno,RedirectAttributes redirectAttributes,
                         PageRequestDTO pageRequestDTO){
        log.info("---------------todo remove---------------");
        log.info("tno:" + tno);

        todoService.remove(tno);

//        redirectAttributes.addAttribute("page",1);
//        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        return "redirect:/todo/list" + pageRequestDTO.getLink();
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,@Valid TodoDTO todoDTO,
                         BindingResult bindingResult,RedirectAttributes redirectAttributes){
        System.out.println("수정 로직 스타또!!!!");
        if(bindingResult.hasErrors()){
            log.info("has errors");
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addAttribute("tno",todoDTO.getTno());
            return "redirect:/todo/modify";
        }

        log.info(todoDTO);
        todoService.modify(todoDTO);

//        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
//        redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
        redirectAttributes.addAttribute("tno",todoDTO.getTno());

        return "redirect:/todo/read";
    }



}
