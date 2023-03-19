package org.zerock.b02.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {

    private Long rno;

    @NotNull //@NotNull:null 만 허용X, "" 이나 " " 은 허용
    private Long bno; //특정 게시물 번호

    @NotEmpty //@NotEmpty:null 과 "" 은 허용X , " " 은 허용
    private String replyText;

    @NotEmpty
    private String replyer;

    //@JsonFormat:JSON 처리시 포맷팅처리
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    //@JsonIgnore:JSON 으로 변환될 때 제외(댓글 수정 시간의 경우 화면에서 출력할 일 없으므로)
    @JsonIgnore
    private LocalDateTime modDate;
}


//참고
//@NotNull : null만 허용X, "" 이나 " " 은 허용
//@NotEmpty : null 과 "" 은 허용X , " " 은 허용
//@NotBlank : null 과 "" 과 " " 모두 허용X