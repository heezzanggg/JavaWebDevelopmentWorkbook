package org.zerock.b02.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Reply",indexes = {
        @Index(name ="idx_reply_board_bno",columnList = "board_bno")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    //다대일 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    //LAZY : 지연로딩 = 기본적으로 필요한 순간까지 데이터베이스와 연결하지않는 방식
    //<->EAGER : 즉시로딩 = 해당 엔티티를 로딩할 때 같이 로딩하는 방식 (Reply 로딩할때 Board도 로딩)
    private Board board;

    private String replyText;

    private String replyer;

    public void changeText(String text){
        this.replyText = text;
    }
}
