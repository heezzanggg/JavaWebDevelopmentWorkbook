package com.example.jdbcex.dao;

import com.example.jdbcex.domain.TodoVO;
import lombok.Cleanup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
//데이터를 전문적으로 처리하는 객체
//일반적으로 데이터베이스의 접근과 처리를 전담하는 객체를 의미하는데 DAO는 주로 VO단위로 처리 함
//TodoService <-> TodoDAO <-> DB

    public String getTime(){

        String now = null;

        try (Connection connection = ConnectionUtil.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select now()");
             ResultSet resultSet = preparedStatement.executeQuery();
            ) {

            resultSet.next();

            now = resultSet.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  now;
    }

    public String getTime2() throws SQLException {

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection. prepareStatement("select now()");
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        //@Cleanup : 해당 메소드가 끝날 때 close()가 자동으로 호출되는것을 보장하는 형태로 작성 가능
        //Lombok라이브러리에 종속적이긴 함
        resultSet.next();

        String now = resultSet.getString(1);

        return now;
    }

    public void insert (TodoVO vo) throws SQLException {
        String sql = "insert into tbl_todo (title, dueDate, finished) values(?,?,?)";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,vo.getTitle());
        preparedStatement.setDate(2, Date.valueOf(vo.getDueDate()));
        preparedStatement.setBoolean(3,vo.isFinished());

        preparedStatement.executeUpdate();
    }

    //tbl_todo내의 모든 데이터 가져오는 기능
    public List<TodoVO> selectAll() throws SQLException {

        String sql = "select * from tbl_todo";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        List<TodoVO> list = new ArrayList<>();
        //테이블의 각 행은 하나의 TodoVO객체가 됨.
        //모든 TodoVO 담기위해 List<TodoVO>타입을 리턴타입으로 지정

        while(resultSet.next()){
            TodoVO vo = TodoVO.builder()
                    .tno(resultSet.getLong("tno"))
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .finished(resultSet.getBoolean("finished"))
                    .build();

            list.add(vo);
        }

        return list;

    }

    //특정 데이터만 가져오는 기능
    public TodoVO selectOne(Long tno) throws SQLException {
        String sql ="select * from tbl_todo where tno=?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1,tno);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        TodoVO vo = TodoVO.builder()
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                .dueDate(resultSet.getDate("dueDate").toLocalDate())
                .finished(resultSet.getBoolean("finished"))
                .build();

        return vo;
    }

    public void deleteOne(Long tno) throws SQLException {

        String sql = "delete from tbl_todo where tno=?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1,tno);
        preparedStatement.executeUpdate();
    }

    public void updateOne(TodoVO todoVO) throws SQLException {

        String sql = "update tbl_todo set title=?, dueDate=?, finished=? where tno=?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,todoVO.getTitle()) ;
        preparedStatement.setDate(2,Date.valueOf(todoVO.getDueDate()));
        preparedStatement.setBoolean(3,todoVO.isFinished());
        preparedStatement.setLong(4,todoVO.getTno());

        preparedStatement.executeUpdate();
    }

}
