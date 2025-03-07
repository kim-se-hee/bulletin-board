package ksh.bulletinboard.domain.board.service;

import ksh.bulletinboard.domain.board.repository.BoardRepository;
import ksh.bulletinboard.domain.board.service.dto.response.BoardServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardServiceResponse> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(BoardServiceResponse::from)
                .toList();
    }

}
