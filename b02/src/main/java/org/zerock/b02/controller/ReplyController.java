package org.zerock.b02.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b02.dto.PageRequestDTO;
import org.zerock.b02.dto.PageResponseDTO;
import org.zerock.b02.dto.ReplyDTO;
import org.zerock.b02.service.ReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
//RestContoller : 메소드의 모든 리턴값은 바로 JSON이나 XML등으로 처리 (JSP,Thymeleaf로 전송 X)
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor //의존성 주입 위해
public class ReplyController {

    private final ReplyService replyService;

//    @ApiOperation(value = "Replies POST", notes = "POST방식으로 댓글 등록") //ApiOperation : Swagger UI에서 해당 기능의 설명으로 출력 됨
//    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE) //PostMapping의 consumes속성 : 해당 메소드를 받아서 소비하는 데이터가 어떤 종류인지 명시 할 수 있음
//    public Map<String,Long> register(@Valid @RequestBody ReplyDTO replyDTO,
//                                                     BindingResult bindingResult) throws BindException {
//        //RequestBody : JSON문자열을 ReplyDTO로 변환하기 위해서 표시
//
//        log.info(replyDTO);
//
//        if(bindingResult.hasErrors()){
//            throw new BindException(bindingResult);
//        }
//
//        Map<String,Long> resultMap = new HashMap<>();
//        resultMap.put("rno",111l);
//
//        log.info(resultMap);
//        return resultMap;
//    }

    //댓글 등록
    @ApiOperation(value = "Replies POST", notes = "POST 방식으로 댓글 등록") //ApiOperation : Swagger UI에서 해당 기능의 설명으로 출력 됨
    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE) //PostMapping의 consumes속성 : 해당 메소드를 받아서 소비하는 데이터가 어떤 종류인지 명시 할 수 있음
    public Map<String,Long> register(@Valid @RequestBody ReplyDTO replyDTO,
                                     BindingResult bindingResult) throws BindException {
        //RequestBody : JSON문자열을 ReplyDTO로 변환하기 위해서 표시

        log.info(replyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String,Long> resultMap = new HashMap<>();

        Long rno = replyService.register(replyDTO);

        resultMap.put("rno",rno);

        return resultMap;
    }

    //특정한 게시물의 댓글 목록 처리 '/replies/list/{bno}'
    @ApiOperation(value = "Replies of Board" , notes = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO){
        //PathVariable : 호출하는 경로의 값을 직접 파라미터의 변수로 처리할 수 있는 방법 제공
        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno,pageRequestDTO);
        log.info("+++++++++++++++++++++++");
        log.info(responseDTO); //PageResponseDTO(page=0, size=0, total=0, start=0, end=0, prev=false, next=false, dtoList=null)
        return responseDTO;
    }

    //특정한 댓글 조회
    @ApiOperation(value = "Read Reply" , notes = "GET 방식으로 특정 댓글 조회")
    @GetMapping(value = "/{rno}")
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno){

        ReplyDTO replyDTO = replyService.read(rno);

        return replyDTO;
    }

    //특정댓글 삭제
    @ApiOperation(value = "Delete Reply" , notes = "DELETE 방식으로 특정 댓글 삭제")
    @DeleteMapping(value = "/{rno}")
    public Map<String,Long> remove(@PathVariable("rno") Long rno){

        replyService.delete(rno);

        Map<String,Long> resultMap = new HashMap<>();

        resultMap.put("rno",rno);

        return resultMap;
    }

    //특정 댓글 수정
    @ApiOperation(value = "Modify Reply" , notes = "PUT 방식으로 특정 댓글 수정")
    @PutMapping (value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> modify(@PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO){

        System.out.println(replyDTO);

        replyDTO.setRno(rno); //번호를 일치시킴

        replyService.modify(replyDTO);

        Map<String,Long> resultMap = new HashMap<>();

        resultMap.put("rno",rno);

        return resultMap;
    }


}
