package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.questionpost.repository.ChildTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChildTagService {

    private final ChildTagRepository childTagRepository;

    @Transactional
    public Long addChildTag(ChildTag childTag) {
        childTagRepository.save(childTag);
        return childTag.getId();
    }

    public ChildTag findById(Long id) {
        return childTagRepository.findById(id).get();
    }

    public List<ChildTag> findChildTagsByParentTag(ParentTag parentTag) {
        return childTagRepository.findByParenttag(parentTag);
    }
}
