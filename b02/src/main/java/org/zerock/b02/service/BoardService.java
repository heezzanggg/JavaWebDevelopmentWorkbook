package org.zerock.b02.service;

import org.zerock.b02.dto.BoardDTO;
import org.zerock.b02.dto.BoardListReplyCountDTO;
import org.zerock.b02.dto.PageRequestDTO;
import org.zerock.b02.dto.PageResponseDTO;

public interface BoardService {

    //등록기능
    Long register(BoardDTO boardDTO);

    //조회기능
    BoardDTO readOne(Long bno);

    //수정기능
    void modify(BoardDTO boardDTO);

    //삭제기능
    void remove(Long bno);

    //목록
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    //댓글의 숫자까지 처리
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);
}
