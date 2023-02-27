package com.example.jdbcex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
}

//@Data : getter/ setter/ toString/ equals /hashCode 등 모두 컴파일 할 때 사용
//VO 와 DTO 내부 구조 같지만 적용되는 어노테이션이 다름
//VO : 주로 읽기 위주의 작업 위해서 사용
//DTO <-> VO : ModelMapper 라이브러리 사용