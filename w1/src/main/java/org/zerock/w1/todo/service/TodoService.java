package org.zerock.w1.todo.service;

import org.zerock.w1.todo.dto.TodoDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//Service : 기능(로직)들의 묶음 , 프로그램이 구현해야하는 기능들의 실제 처리 담당
//여러개의 기능을 정의하고 구현 함
public enum TodoService { //enum타입 클래스
    INSTANCE; //객체 개수결정하는 부분

    //등록 기능
    public void register(TodoDTO todoDTO){
        System.out.println("DEBUG........." + todoDTO);
    }

    //목록
    public List<TodoDTO> getList(){

        List<TodoDTO> todoDTOS = IntStream.range(0,10).mapToObj(i ->{
            TodoDTO dto = new TodoDTO();
            dto.setTno((long)i);
            dto.setTitle("Todo..."+i);
            dto.setDueDate(LocalDate.now());

            return dto;
        }).collect(Collectors.toList());

        return todoDTOS;
    }

    //특정 번호의 조회 기능
    public TodoDTO get(Long tno){

        TodoDTO dto = new TodoDTO();
        dto.setTno(tno);
        dto.setTitle("sample Todo");
        dto.setDueDate(LocalDate.now());
        dto.setFinished(true);

        return dto;
    }
}
