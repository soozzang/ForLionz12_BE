package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.questionpost.repository.ParentTagRepository;
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
public class ParentTagService {

    private final ParentTagRepository parentTagRepository;

    @Transactional
    public Long addParentTag(ParentTag parentTag) {
        parentTagRepository.save(parentTag);
        return parentTag.getId();
    }

    public ParentTag findById(Long id) {
        Optional<ParentTag> parentTag = parentTagRepository.findById(id);
        if (parentTag.isPresent()) {
            return parentTag.get();
        }
        throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
    }

    public List<ParentTag> findAll() {
        return parentTagRepository.findAll();
    }
}
