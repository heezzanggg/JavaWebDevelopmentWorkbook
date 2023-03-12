package org.zerock.springex.mapper;

import org.zerock.springex.domain.TodoVO;
import org.zerock.springex.dto.PageRequestDTO;

import java.util.List;

public interface TodoMapper {
    String getTime();

    //등록
    void insert(TodoVO todoVO);

    //목록기능
    List<TodoVO> selectAll();

    //조회기능
    TodoVO selectOne(Long tno);

    //삭제기능
    void delete(Long tno);

    //수정
    void update(TodoVO todoVO);

    //페이징처리한 목록기능
    List<TodoVO> selectList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);
}
