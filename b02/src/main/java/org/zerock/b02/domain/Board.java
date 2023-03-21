package org.zerock.b02.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bno;

    @Column(length = 500, nullable = false) //칼럼의 길이와 null허용여부
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    public void change(String title,String content){
        this.title = title;
        this.content = content;
    }

    @OneToMany(mappedBy = "board", //BoardImage의 board변수
            cascade = {CascadeType.ALL},//CascadeType.ALL :Board엔티티 객체의 모든 상태변화에 BoardImage 객체들 역시 같이 변경되도록
            fetch = FetchType.LAZY,
            orphanRemoval = true) //하위엔티티의 참조가 더 이상 없는 상태일 경우, 'orphanRemoval = true' 여야지 실제 삭제 발생
    @Builder.Default
    @BatchSize(size=20) //N+1실행되는 쿼리의 보완책, size속성을 지정해서 N번에 해당하는 쿼리를 모아서 한번에 실행 가능
    private Set<BoardImage> imageSet = new HashSet<>();

    //Board객체 자체에서 BoardImage객체 관리
    public void addImage(String uuid, String fileName){

        BoardImage boardImage = BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();

        imageSet.add(boardImage);
    }

    //Board객체 자체에서 BoardImage객체 관리
    public void clearImages(){
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));
        this.imageSet.clear();
    }

}

