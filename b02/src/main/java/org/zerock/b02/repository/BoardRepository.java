package org.zerock.b02.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b02.domain.Board;
import org.zerock.b02.repository.search.BoardSearch;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardSearch {

    @Query(value = "select now()",nativeQuery = true)
    String getTime();

    @EntityGraph(attributePaths = {"imageSet"}) //@EntityGraph의 attributePaths속성 : 같이 로딩해야 하는 속성 명시
    @Query("select b from Board b where b.bno =:bno")
    Optional<Board> findByIdWithImages(Long bno);

}
