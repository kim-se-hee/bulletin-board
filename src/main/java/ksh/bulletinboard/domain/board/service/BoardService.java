package ksh.bulletinboard.domain.board.service;

import ksh.bulletinboard.domain.board.domain.Board;
import ksh.bulletinboard.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> getAllBoards() {
        return boardRepository.findAll().stream()
                .toList();
    }

    public Board getById(long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다"));
    }

}
