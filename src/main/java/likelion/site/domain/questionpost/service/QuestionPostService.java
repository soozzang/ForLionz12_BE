package likelion.site.domain.questionpost.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.dto.response.MemberIdResponseDto;
import likelion.site.domain.member.repository.MemberRepository;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.dto.request.QuestionPostRequestDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostIdResponseDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostResponseDto;
import likelion.site.domain.questionpost.repository.QuestionPostRepository;
import likelion.site.global.exception.exceptions.AuthorizationException;
import likelion.site.global.exception.exceptions.BadElementException;
import likelion.site.global.exception.CustomError;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionPostService {

    private final QuestionPostRepository questionPostRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3Client amazonS3Client;

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
        List<QuestionPostResponseDto> questionPostResponseDtos = new ArrayList<>();

        for (QuestionPost questionPost : questionPosts) {
            QuestionPostResponseDto dto = new QuestionPostResponseDto(questionPost);
            questionPostResponseDtos.add(dto);
        }

        return questionPostResponseDtos;
    }

    public QuestionPostResponseDto findQuestionPostById(Long questionPostId) {
        Optional<QuestionPost> questionPost = questionPostRepository.findById(questionPostId);
        if (questionPost.isPresent()) {
            return new QuestionPostResponseDto(questionPost.get());
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
        questionPost.get().updateQuestionPost(request.getTitle(),request.getContent(),request.getPostImageUrls());
        questionPostRepository.save(questionPost.get());
        return new QuestionPostIdResponseDto(questionPost.get());
    }

    @Transactional
    public QuestionPostIdResponseDto delete(Long questionPostId) {
        QuestionPost questionPost = questionPostRepository.findById(questionPostId).get();
        questionPostRepository.delete(questionPost);
        return new QuestionPostIdResponseDto(questionPost);
    }

    public String convertFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename() + "Q&A" + LocalDateTime.now();
        String fileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
        return fileName;
    }
}
