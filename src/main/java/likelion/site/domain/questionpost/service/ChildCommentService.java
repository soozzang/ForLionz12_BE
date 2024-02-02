package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.ChildComment;
import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.questionpost.repository.ChildCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChildCommentService {

    private final ChildCommentRepository childCommentRepository;

    @Transactional
    public Long addChildComment(ChildComment childComment) {
        childCommentRepository.save(childComment);
        return childComment.getId();
    }

    public List<ChildComment> findChildCommentsByComment(Comment comment) {
        return childCommentRepository.findByComment(comment);
    }
}