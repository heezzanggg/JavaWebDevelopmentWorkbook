package org.zerock.b02.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b02.dto.*;
import org.zerock.b02.service.BoardService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    @Value("${org.zerock.upload.path}")//import시에 springframework으로 시작하는 Value
    private String uploadPath;

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        //PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        //PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);
        PageResponseDTO<BoardListAllDTO> responseDTO = boardService.listWithAll(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO",responseDTO);

    }

    //등록 화면 보기
    @GetMapping("/register")
    @PreAuthorize("hasRole('USER')") //표현식을 이용해 특정한 권한을 가진 사용자만이 접근 가능하도록 지정
    public void registerGET(){

    }

    //등록처리 기능
    @PostMapping("/register")
    public String registerPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        log.info("board POST register");

        if(bindingResult.hasErrors()){
            log.info("has error");
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());

            return "redirect:/board/register";
        }

        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result",bno);
        //addFlashAttribute()로 전달된 데이터는 쿼리스트링으로 처리되지 않아서 브라우저의 경로에는 보이지 않음.
        //따라서 일회성으로 데이터 전송할 때 사용
        return "redirect:/board/list";
    }

    //조회기능 & 수정or삭제 할 수 있는 화면
    @PreAuthorize("isAuthenticated()")//로그인한 사용자만으로 제한
    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        log.info(pageRequestDTO.getLink());

        model.addAttribute("dto", boardDTO);
    }

    //수정기능
    @PreAuthorize("principal.username == #boardDTO.writer") //#boardDTO:현재 파라이터가 수집된 BoardDTO
    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid BoardDTO boardDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        log.info("board modify post..."+boardDTO);

        if(bindingResult.hasErrors()){
            log.info("has errors");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno",boardDTO.getBno());

            return "redirect:/board/modify?"+link;
        }

        boardService.modify(boardDTO);

        redirectAttributes.addFlashAttribute("result","modified");

        redirectAttributes.addAttribute("bno",boardDTO.getBno());

        return "redirect:/board/read";
    }

//    @PostMapping("/remove")
//    public String remove(Long bno, RedirectAttributes redirectAttributes){
//        log.info("remove post.."+bno);
//
//        boardService.remove(bno);
//
//        redirectAttributes.addFlashAttribute("result","removed");
//
//        return "redirect:/board/list";
//    }

    @PreAuthorize("principal.username == #boardDTO.writer")
    @PostMapping("/remove")
    public String remove(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        Long bno = boardDTO.getBno();
        log.info("remove post.."+bno);

        boardService.remove(bno);

        //게시물이 데이터베이스상에서 삭제되었다면 첨부파일 삭제
        log.info(boardDTO.getFileNames());
        List<String> fileNames = boardDTO.getFileNames();
        if(fileNames != null && fileNames.size()>0){
            removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result","removed");

        return "redirect:/board/list";
    }

    public void removeFiles(List<String> files){
        for(String fileName:files){
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();

            try{
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();

                //섬네일 존재하면
                if(contentType.startsWith("image")){
                    File thumbnailFile = new File(uploadPath + File.separator+"s_"+fileName);
                    thumbnailFile.delete();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }//end for
    }

}
