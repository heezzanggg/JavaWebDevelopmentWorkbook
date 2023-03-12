package org.zerock.springex.dto;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDTO {

    private Long tno;
    @NotEmpty //Null,빈문자열X
    private String title;

    @Future//현재보다 미래만 가능
    private LocalDate dueDate;
    private boolean finished;
    @NotEmpty
    private String writer;

}
