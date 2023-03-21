package org.zerock.b02.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class BoardImage implements Comparable<BoardImage>{
    //첨부파일
    //Comparable 인터페이스 적용 하는 이유 : @OneToMany처리에서 순번에 맞게 정렬하기 위해

    @Id
    private String uuid;

    private String fileName;

    private int ord; //순번

    @ManyToOne
    private Board board;

    @Override
    public int compareTo(BoardImage other) {
        return this.ord - other.ord;
    }

    public void changeBoard(Board board){ //Board엔티티 삭제 시 BoardImage객체의 참조도 변경하기위해 사용하는 메소드
        this.board = board;
    }
}
