package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.questionpost.repository.ParentTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return parentTagRepository.findById(id).get();
    }

    public List<ParentTag> findAll() {
        return parentTagRepository.findAll();
    }
}
