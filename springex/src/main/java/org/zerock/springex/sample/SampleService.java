package org.zerock.springex.sample;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@ToString
//생성자 함수 자동으로 작성하는 어노테이션 => 생성자주입 보다 간단히 작성 가능하게 함
@RequiredArgsConstructor
public class SampleService {

    //필드주입방식
//    @Autowired
//    private SampleDAO sampleDAO;
    //'normal'이름을 가진 SampleDAOImpl이 주입 됨
    @Qualifier("normal")
    //생성자 주입 방식 : 객체를 생성할때 문제가 발생하는지 미리 확인 가능
    private final SampleDAO sampleDAO;

}
