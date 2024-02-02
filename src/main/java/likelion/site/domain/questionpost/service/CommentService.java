package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.ChildComment;
import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long addComment(Comment comment) {
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public Long addChildComment(Comment comment, ChildComment childComment) {
        comment.addChildComment(childComment);
        commentRepository.save(comment);
        return childComment.getId();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).get();
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> findCommentsByQuestionPost(QuestionPost questionPost) {
        return commentRepository.findByQuestionPost(questionPost);
    }

}
