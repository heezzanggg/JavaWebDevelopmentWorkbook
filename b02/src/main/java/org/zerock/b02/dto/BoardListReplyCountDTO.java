package org.zerock.b02.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {
//목록화면에 특정한 게시물에 속한 댓글의 숫자 같이 출력 해주기위한 DTO

    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;

    private Long replyCount;
}
