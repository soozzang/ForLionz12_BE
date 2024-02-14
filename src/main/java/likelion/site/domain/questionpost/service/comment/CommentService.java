package likelion.site.domain.questionpost.service.comment;

import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.repository.MemberRepository;
import likelion.site.domain.questionpost.domain.ChildComment;
import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.dto.request.CommentRequestDto;
import likelion.site.domain.questionpost.dto.response.comment.CommentResponseDto;
import likelion.site.domain.questionpost.dto.response.comment.CommentResponseIdDto;
import likelion.site.domain.questionpost.repository.CommentRepository;
import likelion.site.domain.questionpost.repository.QuestionPostRepository;
import likelion.site.global.exception.exceptions.BadElementException;
import likelion.site.global.exception.CustomError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final QuestionPostRepository questionPostRepository;

    @Transactional
    public CommentResponseIdDto addComment(Long memberId, CommentRequestDto request) {
        Member member = memberRepository.findById(memberId).get();
        QuestionPost questionPost = questionPostRepository.findById(request.getQuestionPostId()).get();
        Comment comment = request.toEntity(questionPost, member);
        commentRepository.save(comment);
        return new CommentResponseIdDto(comment);
    }

    @Transactional
    public Long addChildComment(Comment comment, ChildComment childComment) {
        comment.addChildComment(childComment);
        commentRepository.save(comment);
        return childComment.getId();
    }

    public Comment findById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        }
        throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public List<CommentResponseDto> findCommentsByQuestionPost(Long questionPostId) {
        QuestionPost questionPost = questionPostRepository.findById(questionPostId).get();
        List<Comment> comments = commentRepository.findByQuestionPost(questionPost);
        return getCommentResponseDtos(comments);
    }

    private static List<CommentResponseDto> getCommentResponseDtos(List<Comment> comments) {
        List<CommentResponseDto> commentResponses = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponseDto dto = new CommentResponseDto(comment);
            commentResponses.add(dto);
        }
        return commentResponses;
    }

}
