package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import likelion.site.domain.questionpost.dto.request.ChildTagRequestDto;
import likelion.site.domain.questionpost.dto.request.QuestionTagMapRequestDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostResponseDto;
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

    public List<QuestionPostResponseDto> findByChildTag(List<Long> ids) {
        List<ChildTag> childTags = new ArrayList<>();
        List<QuestionPost> questionPosts = new ArrayList<>();
        List<QuestionTagMap> questionTagMaps = new ArrayList<>();

        for (Long id : ids) {
            childTags.add(childTagRepository.findById(id).get());
        }

        for (ChildTag childTag : childTags) {
            List<QuestionTagMap> questionTagMapList = questionTagMapRepository.findByChildTag(childTag);
            questionTagMaps.addAll(questionTagMapList);
            for (QuestionTagMap questionTagMap : questionTagMapList) {
                QuestionPost questionPost = questionTagMap.getQuestionPost();
                if (!questionPosts.contains(questionPost)) {
                    questionPosts.add(questionPost);
                }
            }
        }

        List<QuestionPost> filteredQuestionPosts = new ArrayList<>();

        for (QuestionPost questionPost : questionPosts) {
            int cnt = 0;
            for (QuestionTagMap questionTagMap : questionTagMaps) {
                if (questionPost == questionTagMap.getQuestionPost()) {
                    cnt++;
                }
            }
            if (cnt == ids.size()) {
                filteredQuestionPosts.add(questionPost);
            }
        }

        List<QuestionPostResponseDto> questionPostResponseDtos = new ArrayList<>();

        for (QuestionPost questionPost : filteredQuestionPosts) {
            QuestionPostResponseDto dto = new QuestionPostResponseDto(questionPost);
            questionPostResponseDtos.add(dto);
        }

        return questionPostResponseDtos;
    }
}
