package likelion.site.domain.questionpost.service.tag;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import likelion.site.domain.questionpost.dto.request.QuestionTagMapRequestDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostResponseDto;
import likelion.site.domain.questionpost.repository.ChildTagRepository;
import likelion.site.domain.questionpost.repository.QuestionPostRepository;
import likelion.site.domain.questionpost.repository.QuestionTagMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionTagMapService {

    private final QuestionTagMapRepository questionTagMapRepository;
    private final QuestionPostRepository questionPostRepository;
    private final ChildTagRepository childTagRepository;

    @Transactional
    public void addQuestionTagMap(QuestionTagMapRequestDto request) {
        QuestionPost questionPost = questionPostRepository.findById(request.getQuestionPostId()).get();
        for (Long id : request.getChildTagId()) {
            ChildTag childTag = childTagRepository.findById(id).get();
            QuestionTagMap questionTagMap = request.toEntity(questionPost, childTag);
            questionTagMapRepository.save(questionTagMap);
        }
        return;
    }

    public List<QuestionTagMap> findAllTagMap() {
        return questionTagMapRepository.findAll();
    }

    public List<QuestionPostResponseDto> findByChildTag(List<Long> ids) {
        List<ChildTag> childTags = new ArrayList<>();
        List<QuestionPost> questionPosts = new ArrayList<>();
        List<QuestionTagMap> questionTagMaps = new ArrayList<>();
        List<QuestionPost> filteredQuestionPosts = new ArrayList<>();
        List<QuestionPostResponseDto> questionPostResponseDtos = new ArrayList<>();

        for (Long id : ids) {
            childTags.add(childTagRepository.findById(id).get());
        }
        fillMaps(childTags, questionTagMaps, questionPosts);
        filterQuestionPost(ids, questionPosts, questionTagMaps, filteredQuestionPosts);
        toDto(filteredQuestionPosts, questionPostResponseDtos);
        return questionPostResponseDtos;
    }

    private void toDto(List<QuestionPost> filteredQuestionPosts, List<QuestionPostResponseDto> questionPostResponseDtos) {
        for (QuestionPost questionPost : filteredQuestionPosts) {
            QuestionPostResponseDto dto = new QuestionPostResponseDto(questionPost,getChildTags(questionPost));
            questionPostResponseDtos.add(dto);
        }
    }

    private static void filterQuestionPost(List<Long> ids, List<QuestionPost> questionPosts, List<QuestionTagMap> questionTagMaps, List<QuestionPost> filteredQuestionPosts) {
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
    }

    private void fillMaps(List<ChildTag> childTags, List<QuestionTagMap> questionTagMaps, List<QuestionPost> questionPosts) {
        for (ChildTag childTag : childTags) {
            List<QuestionTagMap> questionTagMapList = questionTagMapRepository.findByChildTag(childTag);
            questionTagMaps.addAll(questionTagMapList);
            fillQuestionPosts(questionPosts, questionTagMapList);
        }
    }

    private static void fillQuestionPosts(List<QuestionPost> questionPosts, List<QuestionTagMap> questionTagMapList) {
        for (QuestionTagMap questionTagMap : questionTagMapList) {
            QuestionPost questionPost = questionTagMap.getQuestionPost();
            if (!questionPosts.contains(questionPost)) {
                questionPosts.add(questionPost);
            }
        }
    }

    private List<String> getChildTags(QuestionPost questionPost) {
        List<QuestionTagMap> questionTagMaps = questionTagMapRepository.findByQuestionPost(questionPost);
        List<String> childTags = new ArrayList<>();
        for (QuestionTagMap questionTagMap : questionTagMaps) {
            childTags.add(questionTagMap.getChildTag().getName());
        }
        return childTags;
    }
}
