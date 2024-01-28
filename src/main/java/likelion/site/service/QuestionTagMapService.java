package likelion.site.service;

import likelion.site.domain.ChildTag;
import likelion.site.domain.QuestionTagMap;
import likelion.site.repository.QuestionTagMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

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
