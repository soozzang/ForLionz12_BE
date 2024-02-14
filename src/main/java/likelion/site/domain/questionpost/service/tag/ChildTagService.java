package likelion.site.domain.questionpost.service.tag;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.questionpost.dto.request.ChildTagRequestDto;
import likelion.site.domain.questionpost.dto.response.tag.ChildTagResponseDto;
import likelion.site.domain.questionpost.dto.response.tag.ChildTagResponseIdDto;
import likelion.site.domain.questionpost.repository.ChildTagRepository;
import likelion.site.domain.questionpost.repository.ParentTagRepository;
import likelion.site.global.exception.exceptions.BadElementException;
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
    private final ParentTagRepository parentTagRepository;

    @Transactional
    public ChildTagResponseIdDto addChildTag(ChildTagRequestDto request) {
        ParentTag parentTag = parentTagRepository.findById(request.getParentTagId()).get();
        ChildTag childTag = request.toEntity(parentTag);
        childTagRepository.save(childTag);
        return new ChildTagResponseIdDto(childTag);
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
