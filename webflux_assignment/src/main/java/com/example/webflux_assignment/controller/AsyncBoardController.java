package com.example.webflux_assignment.controller;

import com.example.webflux_assignment.dto.BoardDto;
import com.example.webflux_assignment.entity.BoardEntity;
import com.example.webflux_assignment.repository.BoardRepository;
import javax.lang.model.type.NullType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/async/board")
@RequiredArgsConstructor
public class AsyncBoardController {

    private final BoardRepository boardRepository;

    @GetMapping
    public Flux<BoardEntity> getBoard(){
        return boardRepository.findAll();
    }

    @PostMapping
    @Transactional
    public Mono<BoardEntity> postBoard(@RequestBody BoardDto boardDto) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setMemberId(boardDto.getMemberId());
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());
        return boardRepository.save(boardEntity);
    }

    @PatchMapping(value = "/{boardId}")
    @Transactional
    public Mono<BoardEntity> patchBoard(@PathVariable long boardId, @RequestBody BoardDto boardDto){
        return boardRepository.findById(boardId)
                .map(b -> {
                    b.setMemberId(boardDto.getMemberId());
                    b.setTitle(boardDto.getTitle());
                    b.setContent(boardDto.getContent());
                    return b;
                })
                .flatMap(b -> boardRepository.save(b));
    }

    @DeleteMapping("/{boardId}")
    public Mono<Void> deleteBoard(@PathVariable long boardId){
        return boardRepository.deleteById(boardId);
    }
}
