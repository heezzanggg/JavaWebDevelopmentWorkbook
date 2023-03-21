package org.zerock.b02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b02.domain.Board;
import org.zerock.b02.dto.*;
import org.zerock.b02.repository.BoardRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
//스프링은 해당 객체를 감싸는 별도의 클래스를 생성 해내는데 간혹 여러번의 데이터베이스 연결이있을 수도 있으므로 트랙잭션 처리는 기본으로 적용 해 둔다
public class BoardServiceImpl implements BoardService {

    private final ModelMapper modelMapper;

    private final BoardRepository boardRepository;

//    @Override
//    public Long register(BoardDTO boardDTO) {
//
//        System.out.println(boardDTO); //(bno=null,
//        Board board = modelMapper.map(boardDTO, Board.class);
//        System.out.println(board); //(bno=0,
//        Long bno = boardRepository.save(board).getBno();
//
//        return bno;
//    }

    @Override
    public Long register(BoardDTO boardDTO) {

        if(boardDTO.getBno()==null){
            System.out.println("boardDTO.getBno() == null");
            boardDTO.setBno(0L);
        }//bno가 null 일경우 dtoToEntity에서 NullPointerException 발생.. 그래서 null열경우 0 넣어줬는데 맞는건가... ㅠㅠ

        Board board = dtoToEntity(boardDTO);
        Long bno = boardRepository.save(board).getBno();

        return bno;
    }

//    @Override
//    public BoardDTO readOne(Long bno) {
//
//        Optional<Board> result = boardRepository.findById(bno);
//
//        Board board = result.orElseThrow(); //orElseThrow: Optional의 인자가 null일 경우 예외처리를 시킨다.
//
//        BoardDTO boardDTO = modelMapper.map(board,BoardDTO.class); //vo->dto
//
//        return boardDTO;
//    }

    @Override
    public BoardDTO readOne(Long bno) {

        //board_image까지 조인처리되는 findByIdWithImages()를 이용
        Optional<Board> result = boardRepository.findByIdWithImages(bno);

        Board board = result.orElseThrow(); //orElseThrow: Optional의 인자가 null일 경우 예외처리를 시킨다.

        System.out.println(board);

        BoardDTO boardDTO = entityToDTO(board); //vo->dto

        System.out.println(boardDTO);

        return boardDTO;
    }

//    @Override
//    public void modify(BoardDTO boardDTO) {
//
//        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
//
//        Board board = result.orElseThrow();
//
//        board.change(boardDTO.getTitle(), boardDTO.getContent());
//
//        boardRepository.save(board);
//    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        Board board = result.orElseThrow();

        board.change(boardDTO.getTitle(), boardDTO.getContent());

        //첨부파일 처리
        board.clearImages();

        if(boardDTO.getFileNames() != null){
            for(String fileName : boardDTO.getFileNames()){
                String[] arr = fileName.split("_");
                board.addImage(arr[0],arr[1]);
            }
        }
        boardRepository.save(board);
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board,BoardDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {

        String[] types =pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types,keyword,pageable);

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoardListAllDTO> result = boardRepository.searchWithAll(types,keyword,pageable);

        return PageResponseDTO.<BoardListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }
}
