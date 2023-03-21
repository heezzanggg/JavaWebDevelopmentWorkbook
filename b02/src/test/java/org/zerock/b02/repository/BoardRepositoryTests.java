package org.zerock.b02.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.b02.domain.Board;
import org.zerock.b02.domain.BoardImage;
import org.zerock.b02.dto.BoardListAllDTO;
import org.zerock.b02.dto.BoardListReplyCountDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;
    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Board board = Board.builder()
                    .title("title...."+i)
                    .content("content...."+i)
                    .writer("user...."+i%10)
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO: "+result.getBno());
        });
    }

    @Test
    public void testSelect(){
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);
//null이 올 수 있는 값을 감싸는 Wrapper 클래스
        Board board = result.orElseThrow();

        log.info(board);
    }

    @Test
    public void testUpdate(){

        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow(); //orElseThrow는 Optional의 인자가 null일 경우 예외처리를 시킨다.

        board.change("update..title 100","update content 100");

        boardRepository.save(board);
    }

    @Test
    public void testDelete(){
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging(){
        //1 Page order by bno desc
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board -> log.info(board));
    }

    @Test
    public void testSearch1(){
        //2page order by bno desc
        Pageable pageable = PageRequest.of(1,10,Sort.by("bno").descending());

        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll(){

        String[] types = {"t","c","w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);

        //total pages
        log.info(result.getTotalPages());

        //page size
        log.info(result.getSize());

        //pageNumber
        log.info(result.getNumber());

        //prev next
        log.info(result.hasPrevious()+":"+result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testSearchReplyCount(){

        String[] types = {"t","c","w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types,keyword,pageable);

        //total pages
        log.info(result.getTotalPages());

        //page size
        log.info(result.getSize());

        //pageNumber
        log.info(result.getNumber());

        //prev next
        log.info(result.hasPrevious()+":"+result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testInsertWithImages(){

        //게시글
        Board board = Board.builder()
                .title("Image Test")
                .content("첨부파일 테스트")
                .writer("tester")
                .build();

        System.out.println(board.toString());

        //첨부파일
        for(int i = 0 ; i<3; i++ ){
            board.addImage(UUID.randomUUID().toString(),"file" + i + ".jpg");
        }

        boardRepository.save(board);
    }

    @Test
    public void testReadWithImages(){

        //반드시 존재하는 bno로 확인
        Optional<Board> result = boardRepository.findByIdWithImages(1L);

        Board board = result.orElseThrow();

        log.info(board);
        log.info("----------------------");
//        log.info(board.getImageSet());
        for(BoardImage boardImage : board.getImageSet()){
            log.info(boardImage);
        };
    }

    @Transactional
    @Commit
    @Test
    public void testModifyImages(){

        Optional<Board> result = boardRepository.findByIdWithImages(1L);

        Board board = result.orElseThrow();

//        System.out.println(result.get().getBno());
//        System.out.println(board.getBno());

        //기존의 첨부파일 모두 삭제
        board.clearImages();

        //새로운 첨부파일들
        for(int i = 0; i<2; i++){
            board.addImage(UUID.randomUUID().toString(),"updateFile"+i+".jpg");
        }

        boardRepository.save(board);
    }

    @Test
    @Transactional
    @Commit
    public void testRemoveAll(){

        Long bno = 1L;

        replyRepository.deleteByBoard_Bno(bno);

        boardRepository.deleteById(bno);
    }

    @Test
    public void testInsertAll(){

        for(int i = 1; i<=100; i++){
            Board board = Board.builder()
                    .title("Title.."+i)
                    .content("Content.."+i)
                    .writer("writer.."+i)
                    .build();

            for(int j = 0; j<3; j++){
                if(i%5==0){
                    continue;
                }
                board.addImage(UUID.randomUUID().toString(),i+"file"+j+".jpg");
            }
            boardRepository.save(board);
        }//end for
    }

    @Test
    @Transactional
    public void testSearchImageReplyCount(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());
        //boardRepository.searchWithAll(null,null,pageable);

        Page<BoardListAllDTO> result = boardRepository.searchWithAll(null,null,pageable);

        log.info("--------------------");
        log.info(result.getTotalElements());

        result.getContent().forEach(boardListAllDTO -> log.info(boardListAllDTO));
    }

}
