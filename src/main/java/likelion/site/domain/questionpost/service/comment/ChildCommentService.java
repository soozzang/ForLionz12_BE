package likelion.site.domain.questionpost.service.comment;

import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.repository.MemberRepository;
import likelion.site.domain.questionpost.domain.ChildComment;
import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.questionpost.dto.request.ChildCommentRequestDto;
import likelion.site.domain.questionpost.dto.request.UpdateChildCommentRequest;
import likelion.site.domain.questionpost.dto.response.comment.ChildCommentIdResponseDto;
import likelion.site.domain.questionpost.dto.response.comment.ChildCommentResponseDto;
import likelion.site.domain.questionpost.repository.ChildCommentRepository;
import likelion.site.domain.questionpost.repository.CommentRepository;
import likelion.site.global.exception.CustomError;
import likelion.site.global.exception.exceptions.AuthorizationException;
import likelion.site.global.exception.exceptions.BadElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChildCommentService {

    private final ChildCommentRepository childCommentRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChildCommentIdResponseDto addChildComment(Long memberId, ChildCommentRequestDto request) {
        Optional<Comment> comment = commentRepository.findById(request.getCommentId());
        Member member = memberRepository.findById(memberId).get();
        if (comment.isEmpty()) {
            throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
        }
        ChildComment childComment = request.toEntity(comment.get(), member);
        childCommentRepository.save(childComment);
        return new ChildCommentIdResponseDto(childComment);
    }

    @Transactional
    public ChildCommentIdResponseDto updateChildComment(Long memberId, UpdateChildCommentRequest request) {
        Member member = memberRepository.findById(memberId).get();
        Optional<ChildComment> childComment = childCommentRepository.findById(request.getChildCommentId());
        if (member != childCommentRepository.findById(request.getChildCommentId()).get().getMember()) {
            throw new AuthorizationException(CustomError.AUTHORIZATION_EXCEPTION);
        }
        if (childComment.isEmpty()) {
            throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
        }
        childComment.get().updateChildComment(request.getContent());
        childCommentRepository.save(childComment.get());
        return new ChildCommentIdResponseDto(childComment.get());
    }

    public List<ChildCommentResponseDto> findChildCommentsByComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
        }
        List<ChildComment> childComments = childCommentRepository.findByComment(comment.get());
        return getChildCommentResponseDtos(childComments);
    }

    private static List<ChildCommentResponseDto> getChildCommentResponseDtos(List<ChildComment> childComments) {
        List<ChildCommentResponseDto> childCommentResponses = new ArrayList<>();

        for (ChildComment childComment : childComments) {
            ChildCommentResponseDto dto = new ChildCommentResponseDto(childComment);
            childCommentResponses.add(dto);
        }
        return childCommentResponses;
    }
}
