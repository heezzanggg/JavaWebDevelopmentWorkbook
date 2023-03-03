package org.zerock.springex.sample;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
//JUnit5버전에서 'spring-test'를 이용하기 위한 설정
@ExtendWith(SpringExtension.class)
//스프링의 설정 정보를 로딩하기 위해서 사용하는 어노테이션 (XML설정 : location 속성 이용 / 자바설정 : classes속성 이용)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class SampleTests {

    //의존성 주입 관련 어노테이션. 만일 해당 타입의 빈(Bean)이 존재한면 여기에 주입해 주기를 원한다.
    //필드주입(Field Injection) : 멤버변수에 직접 @Autowired 선언하는 방식
    @Autowired
    private SampleService sampleService;
    @Autowired
    private DataSource dataSource;

    @Test
    public void testService1(){
        log.info(sampleService);
        Assertions.assertNotNull(sampleService);
    }

    @Test
    public void testConnection() throws Exception {
        Connection connection = dataSource.getConnection();
        log.info(connection);
        Assertions.assertNotNull(connection);

        connection.close();
    }
}
