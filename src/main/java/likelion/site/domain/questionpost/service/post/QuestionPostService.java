package likelion.site.domain.questionpost.service.post;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.repository.MemberRepository;
import likelion.site.domain.questionpost.domain.Likes;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import likelion.site.domain.questionpost.dto.request.QuestionPostRequestDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostDetailResponseDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostIdResponseDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostResponseDto;
import likelion.site.domain.questionpost.repository.LikesRepository;
import likelion.site.domain.questionpost.repository.QuestionPostRepository;
import likelion.site.domain.questionpost.repository.QuestionTagMapRepository;
import likelion.site.global.exception.CustomError;
import likelion.site.global.exception.exceptions.AuthorizationException;
import likelion.site.global.exception.exceptions.BadElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionPostService {

    private final QuestionPostRepository questionPostRepository;
    private final MemberRepository memberRepository;
    private final QuestionTagMapRepository questionTagMapRepository;
    private final AmazonS3Client amazonS3Client;
    private final LikesRepository likesRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public QuestionPostIdResponseDto addQuestionPost(Long id, QuestionPostRequestDto request) {
        Member member = memberRepository.findById(id).get();
        QuestionPost questionPost = request.toEntity(member);
        questionPostRepository.save(questionPost);
        return new QuestionPostIdResponseDto(questionPost);
    }

    public List<QuestionPostResponseDto> findAllQuestionPosts() {
        List<QuestionPost> questionPosts = questionPostRepository.findAll();
        return getQuestionPostResponseDtos(questionPosts);
    }

    private List<QuestionPostResponseDto> getQuestionPostResponseDtos(List<QuestionPost> questionPosts) {
        List<QuestionPostResponseDto> questionPostResponseDtos = new ArrayList<>();

        for (QuestionPost questionPost : questionPosts) {
            List<String> childTags = getChildTags(questionPost);
            QuestionPostResponseDto dto = new QuestionPostResponseDto(questionPost,childTags);
            questionPostResponseDtos.add(dto);
        }
        return questionPostResponseDtos;
    }

    private List<String> getChildTags(QuestionPost questionPost) {
        List<QuestionTagMap> questionTagMaps = questionTagMapRepository.findByQuestionPost(questionPost);
        List<String> childTags = new ArrayList<>();
        for (QuestionTagMap questionTagMap : questionTagMaps) {
            childTags.add(questionTagMap.getChildTag().getName());
        }
        return childTags;
    }

    public QuestionPostDetailResponseDto findQuestionPostById(Long questionPostId, Long memberId) {
        Optional<QuestionPost> questionPost = questionPostRepository.findById(questionPostId);
        Member member = memberRepository.findById(memberId).get();
        if (questionPost.isPresent()) {
            return new QuestionPostDetailResponseDto(questionPost.get(),getChildTags(questionPost.get()), isLiked(questionPost.get(),member));
        }
        throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
    }

    @Transactional
    public QuestionPostIdResponseDto update(Long questionId, Long memberId, QuestionPostRequestDto request) {
        Optional<QuestionPost> questionPost = questionPostRepository.findById(questionId);
        Member member = memberRepository.findById(memberId).get();

        if (questionPost.isEmpty()) {
            throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
        }
        if (member != questionPost.get().getMember()) {
            throw new AuthorizationException(CustomError.AUTHORIZATION_EXCEPTION);
        }
        questionTagMapRepository.deleteAll(questionTagMapRepository.findByQuestionPost(questionPost.get()));
        questionPost.get().updateQuestionPost(request.getTitle(),request.getContent(),request.getPostImageUrls());
        questionPostRepository.save(questionPost.get());
        return new QuestionPostIdResponseDto(questionPost.get());
    }

    @Transactional
    public QuestionPostIdResponseDto delete(Long questionPostId) {
        QuestionPost questionPost = questionPostRepository.findById(questionPostId).get();
        questionPostRepository.delete(questionPost);
        questionTagMapRepository.deleteAll(questionTagMapRepository.findByQuestionPost(questionPost));
        return new QuestionPostIdResponseDto(questionPost);
    }

    @Transactional
    public QuestionPostIdResponseDto like(Long questionPostId, Long memberId) {
        QuestionPost questionPost = questionPostRepository.findById(questionPostId).get();
        Member member = memberRepository.findById(memberId).get();
        if (likesRepository.findByQuestionPostAndMember(questionPost, member).isEmpty()) {
            likesRepository.save(createLikes(questionPostId, memberId));
            return new QuestionPostIdResponseDto(questionPost);
        }
        likesRepository.deleteLikesById(likesRepository.findByQuestionPostAndMember(questionPost,member).get().getId());
        return new QuestionPostIdResponseDto(questionPost);
    }

    public Likes createLikes(Long questionPostId, Long memberId) {
        QuestionPost questionPost = questionPostRepository.findById(questionPostId).get();
        Member member = memberRepository.findById(memberId).get();
        return Likes.builder()
                .member(member)
                .questionPost(questionPost)
                .build();
    }

    public boolean isLiked(QuestionPost questionPost, Member member) {
        return likesRepository.findByQuestionPostAndMember(questionPost, member).isPresent();
    }

    public String convertFile(MultipartFile file) throws IOException {
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "_") + "Q&A" + LocalDateTime.now();
        String fileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
        return fileUrl;
    }
}
