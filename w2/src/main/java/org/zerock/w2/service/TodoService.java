package org.zerock.w2.service;


import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.zerock.w2.dao.TodoDAO;
import org.zerock.w2.domain.TodoVO;
import org.zerock.w2.dto.TodoDTO;
import org.zerock.w2.util.MapperUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public enum TodoService {
    INSTANCE;

    private TodoDAO dao; //DAO(Data Access Object)
    private ModelMapper modelMapper; //ModelMapper 이용하여 DTO <->Vo 변환

    TodoService(){
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    //register()는 TodoDTO를 파라미터로 받아서 TodoVO로 변환하고, 이를 저장함 (dto -> vo)
    public void register(TodoDTO todoDTO) throws SQLException {
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        //System.out.println("todoVO:"+todoVO);
        log.info(todoVO);
        dao.insert(todoVO);//int를 반환하므로 이를 이용해서 예외처리도 가능
    }
    //TodoDAO에서 가져온 TodoVO목록 -> TodoDTO (vo -> dto)
    public List<TodoDTO> listAll() throws SQLException {
        //TodoDAO에서 가져온 TodoVO목록
        List<TodoVO>  voList = dao.selectAll();
        log.info("voList..........");
        log.info(voList);

        //TodoVO목록을 TodoDTO로 변환
        List<TodoDTO> dtoList = voList.stream()
                .map(vo->modelMapper.map(vo,TodoDTO.class))
                .collect(Collectors.toList());
        log.info("dtoList........");
        log.info(dtoList);
        return dtoList;
    }

    //DTO반환하고 컨틀롤러에 담아서 JSP에 출력 (vo -> dto)
    public TodoDTO get(Long tno) throws SQLException {
        log.info("tno:"+tno);
        TodoVO todoVO = dao.selectOne(tno);  //TodoDAO의 selectOne()을 통해서 TodoVO객체 가져오고
        TodoDTO todoDTO = modelMapper.map(todoVO,TodoDTO.class); // ModelMapper를 통해서 vo -> dto
        return todoDTO;
    }

    public void remove(Long tno) throws SQLException {
        log.info("tno:"+tno);
        dao.deleteOne(tno);
    }
    //DTO->VO
    public void modify(TodoDTO todoDTO) throws SQLException {
        log.info("todoDTO:"+todoDTO);
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        dao.updateOne(todoVO);
    }

}
