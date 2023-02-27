package org.zerock.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectTests {

    @Test
    //@Test 적용하는 메소드는 반드시 public으로 선언되어야 하고, 파라미터나 리턴타입 없이 작성
    public void test1(){
        int v1 = 10;
        int v2 = 10;

        Assertions.assertEquals(v1,v2);
    }

    @Test
    public void testConnection() throws ClassNotFoundException, SQLException {
        //testConnection()은 자바코드를 이용해서 설치된 DB와 연결을 확인하는 용도의 코드
        //테스트코드 실행하면 단순히 실행의 성공여부 결과만 확인 할 수 있음

        //JDBC드라이버클래스를 메모리상으로 로딩하는 역할
        Class.forName("com.mysql.cj.jdbc.Driver");

        //Connection : 데이터베이스와 네트워크 연결을 의미
        //DriverManager.getConnection() : DB내에있는 여러 정보들을 통해서 특정한 데이터베이스에 연결을 시도
        Connection connection= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sys",
                "root",
                "1234");
        //jdbc프로토콜 이용한다는 의미, localhost:3306은네트워크 연결정보, sys는 연결하려는 DB의 정보
        //webuser : 연결을 위해서는 사용자의 계정과 패스워드가 필요
        Assertions.assertNotNull(connection);
        //DB와 정상적으로 연결이 된다면 Connecgtion타입의 객체는 null이 아니라는 것을 확신한다는 의미
        connection.close();
        //DB연결 종료
    }

    @Test
    public void testHikariCP() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/sys");
        config.setUsername("root");
        config.setPassword("1234");
        config.addDataSourceProperty("cachePrepStmts","true");
        config.addDataSourceProperty("prepStmtCacheSize","250");
        config.addDataSourceProperty("PrepStmtCacheSqlLimit","2048");

        HikariDataSource ds = new HikariDataSource(config);
        Connection connection = ds.getConnection();

        System.out.println(connection);

        connection.close();
    }
}
