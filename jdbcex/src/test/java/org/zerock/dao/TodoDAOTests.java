package org.zerock.dao;

import com.example.jdbcex.dao.TodoDAO;
import com.example.jdbcex.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {

    private TodoDAO todoDAO;

    @BeforeEach
    //모든 테스트 전에 TodoDAO타입의 객체 생성하도록
    public void ready(){
        todoDAO=new TodoDAO();
    }

    @Test
    public void testTime(){
        System.out.println(todoDAO.getTime());
    }

    @Test
    public void testInsert() throws SQLException {
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title...")
                .dueDate(LocalDate.of(2021,12,31))
                .build();

        todoDAO.insert(todoVO);
    }
    //빌더패턴 : 생성자와 달리 필요한 만큼만 데이터를 세팅할 수 있음

    @Test
    public void testList() throws SQLException {
        List<TodoVO> list = todoDAO.selectAll();
        list.forEach(vo->System.out.println(vo));
    }

    @Test
    public void testSelectOne() throws SQLException {
        Long tno = 3L; //반드시 존재하는 번호를 이용
        TodoVO vo = todoDAO.selectOne(tno);
        System.out.println(vo);
        //TodoVO(tno=3, title=스프링 제목 수정, dueDate=2022-12-30, finished=false)

    }

    @Test
    public void testUpdateOne() throws SQLException {
        TodoVO todoVO = TodoVO.builder()
                .tno(1L)
                .title("Sample Title...")
                .dueDate(LocalDate.of(2021,12,31))
                .finished(true)
                .build();

        todoDAO.updateOne(todoVO);
    }
}
