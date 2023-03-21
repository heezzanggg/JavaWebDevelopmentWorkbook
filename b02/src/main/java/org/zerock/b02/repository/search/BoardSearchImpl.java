package org.zerock.b02.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b02.domain.Board;
import org.zerock.b02.domain.QBoard;
import org.zerock.b02.domain.QReply;
import org.zerock.b02.dto.BoardImageDTO;
import org.zerock.b02.dto.BoardListAllDTO;
import org.zerock.b02.dto.BoardListReplyCountDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        QBoard board = QBoard.board; //Q도메인 객체

        JPQLQuery<Board> query = from(board); //select..from board

        BooleanBuilder booleanBuilder = new BooleanBuilder();//Querydsl 이용할 떄 '()'필요한 상황시 BooleanBuilder 이용 =>(

        booleanBuilder.or(board.title.contains("11")); //title like...

        booleanBuilder.or(board.content.contains("11")); //content like...

        query.where(booleanBuilder);
        query.where(board.bno.gt(0L));

        query.where(board.title.contains("1")); // where title like...

        //paging
        this.getQuerydsl().applyPagination(pageable,query); //=>쿼리에 limit 적용됨

        List<Board> list = query.fetch(); //fetch() JPQLQuery의 실행

        long count = query.fetchCount(); //fetchCount() count쿼리 실행 가능

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board; //Q도메인 객체
        JPQLQuery<Board> query = from(board); //select..from board

        if((types !=null && types.length>0) && keyword != null){ //검색조건과 키워드가 있다면
            BooleanBuilder booleanBuilder = new BooleanBuilder();  //Querydsl 이용할 떄 '()'필요한 상황시 BooleanBuilder 이용=>(
            for(String type: types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno>0
        query.where(board.bno.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable,query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list,pageable,count);
        //list<T> 실제 목록 데이터, Pageable 페이지 관련 정보를 가진 객체, long 전체갯수
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

        //Q도메인 객체
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));

        query.groupBy(board);

        //검색조건
        if((types!=null &&types.length>0)&&keyword !=null){ //검색조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); //Querydsl 이용할 떄 '()'필요한 상황시 BooleanBuilder 이용=>(

            for(String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }
        //bno > 0
        query.where(board.bno.gt(0L));

        //Projections : JPQL의 결과를 바로 DTO로 처리하는 기능
        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.bean(BoardListReplyCountDTO.class,
                board.bno,board.title,board.writer,board.regDate,reply.count().as("replyCount")));
    //        select board.bno, board.title, board.writer, board.regDate, count(reply) as replyCount
    //        from Board board
    //        left join Reply reply with reply.board = board
    //        where (board.title like ?1 escape '!' or board.content like ?2 escape '!' or board.writer like ?3 escape '!') and board.bno > ?4
    //        group by board

        this.getQuerydsl().applyPagination(pageable,dtoQuery); //쿼리문이군....
    //        select board.bno, board.title, board.writer, board.regDate, count(reply) as replyCount
    //        from Board board
    //        left join Reply reply with reply.board = board
    //        where (board.title like ?1 escape '!' or board.content like ?2 escape '!' or board.writer like ?3 escape '!') and board.bno > ?4
    //        group by board
    //        order by board.bno desc, board.bno desc

        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();
        System.out.println(dtoList);
        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList,pageable,count);

    }

//    @Override
//    public Page<BoardListReplyCountDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {
//    //Board, Reply를 lfet join 처리하고 쿼리 실행해서 내용 확인
//        QBoard board = QBoard.board;
//        QReply reply = QReply.reply;
//
//        JPQLQuery<Board> boardJPQLQuery = from(board);
//        boardJPQLQuery.leftJoin(reply).on(reply.board.eq(board));//left join
//
//        getQuerydsl().applyPagination(pageable,boardJPQLQuery);//paging
//
//        List<Board> boardList = boardJPQLQuery.fetch();
//
//        boardList.forEach(board1 -> {
//            System.out.println(board1.getBno());
//            System.out.println(board1.getImageSet());
//            System.out.println("-------------------");
//        });
//
//        return null;
//    }

    @Override
    public Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> boardJPQLQuery = from(board);
        boardJPQLQuery.leftJoin(reply).on(reply.board.eq(board)); //left join

        //검색조건
        if( (types != null && types.length > 0) && keyword != null ){

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }//end for
            boardJPQLQuery.where(booleanBuilder);
        }

        boardJPQLQuery.groupBy(board);

        getQuerydsl().applyPagination(pageable,boardJPQLQuery);//paging

        JPQLQuery<Tuple> tupleJPQLQuery = boardJPQLQuery.select(board,reply.countDistinct());

        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<BoardListAllDTO> dtoList = tupleList.stream().map(tuple -> {

            Board board1 = (Board) tuple.get(board);
            long replyCount = tuple.get(1,Long.class);

            BoardListAllDTO dto = BoardListAllDTO.builder()
                    .bno(board1.getBno())
                    .title(board1.getTitle())
                    .writer(board1.getWriter())
                    .regDate(board1.getRegDate())
                    .replyCount(replyCount)
                    .build();

            //BoardImage를 BoardImageDTO 처리할 부분
            List<BoardImageDTO> imageDTOS = board1.getImageSet().stream().sorted()
                    .map(boardImage -> BoardImageDTO.builder()
                            .uuid(boardImage.getUuid())
                            .fileName(boardImage.getFileName())
                            .ord(boardImage.getOrd())
                            .build()
                    ).collect(Collectors.toList());

            dto.setBoardImages(imageDTOS); //처리된 BoardImageDTO들을 추가

            return dto;
        }).collect(Collectors.toList());

        long totalCount = boardJPQLQuery.fetchCount();


        return new PageImpl<>(dtoList, pageable, totalCount);
    }

}
