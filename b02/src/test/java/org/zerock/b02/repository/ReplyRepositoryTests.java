package org.zerock.b02.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b02.domain.Board;
import org.zerock.b02.domain.Reply;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert(){

        //실제 DB에있는 bno
        Long bno = 100L;

        Board board = Board.builder().bno(100L).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyer("댓글....")
                .replyText("replyer1").build();

        replyRepository.save(reply);
    }

    @Test
    public  void testBoardReplies(){

        Long bno = 100L;

        Pageable pageable = PageRequest.of(0,10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno,pageable);
        log.info(result.getContent());

        result.getContent().forEach(reply -> log.info(reply));
    }



}
