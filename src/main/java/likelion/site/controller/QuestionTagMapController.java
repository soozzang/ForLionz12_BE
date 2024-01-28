package likelion.site.controller;

import likelion.site.domain.ChildTag;
import likelion.site.domain.QuestionPost;
import likelion.site.domain.QuestionTagMap;
import likelion.site.service.ChildTagService;
import likelion.site.service.QuestionPostService;
import likelion.site.service.QuestionTagMapService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questionTagMap")
public class QuestionTagMapController {

    private final QuestionTagMapService questionTagMapService;
    private final QuestionPostService questionPostService;
    private final ChildTagService childTagService;

    @PostMapping
    public ResponseEntity<QuestionTagMapResponseIdDto> createQuestionTagMap(@RequestBody QuestionTagMapRequestDto request) {
        QuestionPost questionPost = questionPostService.findQuestionPostById(request.questionPostId);
        ChildTag childTag = childTagService.findById(request.childTagId);
        QuestionTagMap questionTagMap = QuestionTagMap.builder()
                .questionPost(questionPost)
                .childTag(childTag)
                .build();
        Long id = questionTagMapService.addQuestionTagMap(questionTagMap);
        return ResponseEntity.ok().body(new QuestionTagMapResponseIdDto(questionTagMap));
    }

    @GetMapping
    public ResponseEntity<Result> getQuestionTagMap(@RequestParam Long childTagId) {
        ChildTag childTag = childTagService.findById(childTagId);
        List<QuestionTagMap> questionTagMaps = questionTagMapService.findByChildTag(childTag);
        List<QuestionTagMapResponseDto> collect = questionTagMaps.stream()
                .map(QuestionTagMapResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
    }

    @Data
    public static class QuestionTagMapResponseIdDto {
        Long id;

        public QuestionTagMapResponseIdDto(QuestionTagMap questionTagMap) {
            this.id = questionTagMap.getId();
        }
    }

    @Data
    public static class QuestionTagMapResponseDto {
        Long id;
        Long childTagId;
        Long questionPostId;

        public QuestionTagMapResponseDto(QuestionTagMap questionTagMap) {
            this.id = questionTagMap.getId();
            this.childTagId = questionTagMap.getChildTag().getId();
            this.questionPostId = questionTagMap.getQuestionPost().getId();
        }
    }

    @Data
    public static class QuestionTagMapRequestDto {
        Long questionPostId;
        Long childTagId;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
