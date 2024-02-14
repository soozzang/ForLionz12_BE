package likelion.site.domain.questionpost.service.tag;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import likelion.site.domain.questionpost.dto.request.ParentTagRequestDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostResponseDto;
import likelion.site.domain.questionpost.dto.response.tag.ChildTagResponseDto;
import likelion.site.domain.questionpost.dto.response.tag.ParentTagResponseDto;
import likelion.site.domain.questionpost.dto.response.tag.ParentTagResponseIdDto;
import likelion.site.domain.questionpost.dto.response.tag.SearchByParentTagResponse;
import likelion.site.domain.questionpost.repository.ChildTagRepository;
import likelion.site.domain.questionpost.repository.ParentTagRepository;
import likelion.site.domain.questionpost.repository.QuestionTagMapRepository;
import likelion.site.global.exception.exceptions.BadElementException;
import likelion.site.global.exception.CustomError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParentTagService {

    private final ParentTagRepository parentTagRepository;
    private final ChildTagRepository childTagRepository;
    private final QuestionTagMapRepository questionTagMapRepository;

    @Transactional
    public ParentTagResponseIdDto addParentTag(ParentTagRequestDto request) {
        ParentTag parentTag = request.toEntity();
        parentTagRepository.save(parentTag);
        return new ParentTagResponseIdDto(parentTag);
    }

    public ParentTag findById(Long id) {
        Optional<ParentTag> parentTag = parentTagRepository.findById(id);
        if (parentTag.isPresent()) {
            return parentTag.get();
        }
        throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
    }

    public List<ParentTagResponseDto> findAll() {
        List<ParentTag> parentTags = parentTagRepository.findAll();
        List<ParentTagResponseDto> parentTagResponseDtos = new ArrayList<>();

        for (ParentTag parentTag : parentTags) {
            ParentTagResponseDto dto = new ParentTagResponseDto(parentTag);
            parentTagResponseDtos.add(dto);
        }
        return parentTagResponseDtos;
    }

    public SearchByParentTagResponse findChildsAndPosts(Long parentTagId) {
        ParentTag parentTag = parentTagRepository.findById(parentTagId).get();
        List<ChildTag> childTags = childTagRepository.findByParenttag(parentTag);
        List<ChildTagResponseDto> childTagResponseDtos = new ArrayList<>();
        List<QuestionPostResponseDto> questionPostResponseDtos = new ArrayList<>();
        HashSet<QuestionPost> questionPosts = new HashSet<>();

        makeQuestionPostList(childTags, childTagResponseDtos, questionPosts);
        toDto(questionPosts, questionPostResponseDtos);

        return new SearchByParentTagResponse(childTagResponseDtos, questionPostResponseDtos);
    }

    private void toDto(HashSet<QuestionPost> questionPosts, List<QuestionPostResponseDto> questionPostResponseDtos) {
        for (QuestionPost questionPost : questionPosts) {
            QuestionPostResponseDto dto = new QuestionPostResponseDto(questionPost, getChildTags(questionPost));
            questionPostResponseDtos.add(dto);
        }
    }

    private void makeQuestionPostList(List<ChildTag> childTags, List<ChildTagResponseDto> childTagResponseDtos, HashSet<QuestionPost> questionPosts) {
        for (ChildTag childTag : childTags) {
            ChildTagResponseDto dto = new ChildTagResponseDto(childTag);
            childTagResponseDtos.add(dto);

            List<QuestionTagMap> questionTagMaps = questionTagMapRepository.findByChildTag(childTag);
            for (QuestionTagMap questionTagMap : questionTagMaps) {
                QuestionPost questionPost = questionTagMap.getQuestionPost();
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
