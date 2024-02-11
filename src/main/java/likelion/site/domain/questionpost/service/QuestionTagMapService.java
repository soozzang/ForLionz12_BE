package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import likelion.site.domain.questionpost.dto.request.ChildTagRequestDto;
import likelion.site.domain.questionpost.dto.request.QuestionTagMapRequestDto;
import likelion.site.domain.questionpost.dto.response.tag.QuestionTagMapResponseDto;
import likelion.site.domain.questionpost.dto.response.tag.QuestionTagMapResponseIdDto;
import likelion.site.domain.questionpost.repository.ChildTagRepository;
import likelion.site.domain.questionpost.repository.QuestionPostRepository;
import likelion.site.domain.questionpost.repository.QuestionTagMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionTagMapService {

    private final QuestionTagMapRepository questionTagMapRepository;
    private final QuestionPostRepository questionPostRepository;
    private final ChildTagRepository childTagRepository;

    @Transactional
    public QuestionTagMapResponseIdDto addQuestionTagMap(QuestionTagMapRequestDto request) {
        QuestionPost questionPost = questionPostRepository.findById(request.getQuestionPostId()).get();
        ChildTag childTag = childTagRepository.findById(request.getChildTagId()).get();
        QuestionTagMap questionTagMap = request.toEntity(questionPost, childTag);
        questionTagMapRepository.save(questionTagMap);
        return new QuestionTagMapResponseIdDto(questionTagMap);
    }

    public List<QuestionTagMap> findAllTagMap() {
        return questionTagMapRepository.findAll();
    }

    public List<QuestionTagMapResponseDto> findByChildTag(List<Long> ids) {
        List<ChildTag> childTags = new ArrayList<>();

        for (Long id : ids) {
            childTags.add(childTagRepository.findById(id).get());
        }

        HashSet<QuestionTagMap> questionTagMaps = new HashSet<>();

        for (ChildTag childTag : childTags) {
            List<QuestionTagMap> questionTagMapList = questionTagMapRepository.findByChildTag(childTag);
            questionTagMaps.addAll(questionTagMapList);
        }

        List<QuestionTagMapResponseDto> questionTagMapResponseDtos = new ArrayList<>();

        for (QuestionTagMap questionTagMap : questionTagMaps) {
            QuestionTagMapResponseDto dto = new QuestionTagMapResponseDto(questionTagMap);
            questionTagMapResponseDtos.add(dto);
        }

        return questionTagMapResponseDtos;
    }
}
