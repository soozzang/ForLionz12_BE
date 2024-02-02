package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import likelion.site.domain.questionpost.repository.QuestionTagMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionTagMapService {

    private final QuestionTagMapRepository questionTagMapRepository;

    @Transactional
    public Long addQuestionTagMap(QuestionTagMap questionTagMap) {
        questionTagMapRepository.save(questionTagMap);
        return questionTagMap.getId();
    }

    public List<QuestionTagMap> findByChildTag(ChildTag childTag) {
        return questionTagMapRepository.findByChildTag(childTag);
    }

}
