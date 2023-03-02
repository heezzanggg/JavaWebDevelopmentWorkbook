package org.zerock.w2.dao;

import lombok.Cleanup;
import org.zerock.w2.domain.MemberVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//데이터를 전문적으로 처리하는 객체
//일반적으로 데이터베이스의 접근과 처리를 전담하는 객체를 의미하는데 DAO는 주로 VO단위로 처리 함
public class MemberDAO {

    public MemberVO getWithPassword(String mid,String mpw) throws Exception {
        String query = "select mid, mpw,mname from tbl_member where mid=? and mpw=?";

        MemberVO memberVO = null;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,mid);
        preparedStatement.setString(2,mpw);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        //@Cleanup : 해당 메소드가 끝날 때 close()가 자동으로 호출되는것을 보장하는 형태로 작성 가능
        //Lombok라이브러리에 종속적이긴 함

        resultSet.next();

        memberVO = MemberVO.builder()
                .mid(resultSet.getString(1))
                .mpw(resultSet.getString(2))
                .mname(resultSet.getString(3))
                .build();

        return memberVO;

    }

    public void updateUuid(String mid, String uuid) throws Exception {
        String sql = "update tbl_member set uuid=? where mid=?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,uuid);
        preparedStatement.setString(2,mid);
        preparedStatement.executeUpdate();
    }

    //쿠키값을 이용해서 사용자 조회
    public MemberVO selectUUID(String uuid) throws Exception {
        String query = "select mid,mpw,mname,uuid from tbl_member where uuid =?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,uuid);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        MemberVO memberVO = MemberVO.builder()
                .mid(resultSet.getString(1))
                .mpw(resultSet.getString(2))
                .mname(resultSet.getString(3))
                .uuid(resultSet.getString(4))
                .build();

        return memberVO;
    }


}
