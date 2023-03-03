package org.zerock.springex.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Log4j2
//JUnit5버전에서 'spring-test'를 이용하기 위한 설정
@ExtendWith(SpringExtension.class)
//스프링의 설정 정보를 로딩하기 위해서 사용하는 어노테이션 (XML설정 : location 속성 이용 / 자바설정 : classes속성 이용)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class TimeMapperTests {

    //@Autowired 의 required 속성 false : 의존객체를 주입받지 못하더라도 빈을 생성하도록 할 수 있다.
    @Autowired(required = false)
    private TimeMapper timeMapper;

    @Test
    public void testGetTime(){
        log.info(timeMapper.getTime());
    }

}
