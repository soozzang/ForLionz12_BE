package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.questionpost.repository.ChildTagRepository;
import likelion.site.global.exception.BadElementException;
import likelion.site.global.exception.CustomError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<ChildTag> childTag = childTagRepository.findById(id);
        if (childTag.isPresent()) {
            return childTag.get();
        }
        throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
    }

    public List<ChildTag> findChildTagsByParentTag(ParentTag parentTag) {
        return childTagRepository.findByParenttag(parentTag);
    }
}
