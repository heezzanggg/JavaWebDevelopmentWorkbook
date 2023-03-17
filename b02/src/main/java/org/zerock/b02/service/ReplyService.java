package org.zerock.b02.service;

import org.zerock.b02.dto.PageRequestDTO;
import org.zerock.b02.dto.PageResponseDTO;
import org.zerock.b02.dto.ReplyDTO;

public interface ReplyService {

    //댓글 등록
    Long register(ReplyDTO replyDTO);

    //댓글 조회
    ReplyDTO read(Long rno);

    //댓글 수정 => replyText만 수정 가능
    void modify(ReplyDTO replyDTO);

    //댓글 삭제
    void delete(Long rno);

    //특정 게시물의 댓글 목록을 페이징 처리
    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);


}
