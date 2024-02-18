package likelion.site.domain.member.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.dto.request.*;
import likelion.site.domain.member.dto.response.MemberIdResponseDto;
import likelion.site.domain.member.dto.response.MemberMyInfoResponse;
import likelion.site.domain.member.dto.response.MemberResponseDto;
import likelion.site.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public MemberResponseDto findMemberInfoById(Long memberId) {
        Member member =  memberRepository.findById(memberId).get();
        return new MemberResponseDto(member);
    }

    public MemberMyInfoResponse findMyinfo(Long memberId) {
        Member member =  memberRepository.findById(memberId).get();
        return new MemberMyInfoResponse(member);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public MemberIdResponseDto updateIntroduction(Long id, IntroductionUpdateRequest request) {
        Member member = memberRepository.findById(id).get();
        member.updateIntroduction(request.getIntroduction());
        memberRepository.save(member);
        return new MemberIdResponseDto(member);
    }

    @Transactional
    public MemberIdResponseDto updateGithubAddress(Long id, GithubAddressUpdateRequest request) {
        Member member = memberRepository.findById(id).get();
        member.updateGithubAddress(request.getGithubAddress());
        memberRepository.save(member);
        return new MemberIdResponseDto(member);
    }

    @Transactional
    public MemberIdResponseDto updatePassword(Long id, PasswordUpdateRequest request) {
        Member member = memberRepository.findById(id).get();
        member.updatePassword(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(member);
        return new MemberIdResponseDto(member);
    }

    @Transactional
    public MemberIdResponseDto updateInstagramId(Long id, InstagramIdUpdateRequest request) {
        Member member = memberRepository.findById(id).get();
        member.updateInstagramId(request.getInstagramId());
        memberRepository.save(member);
        return new MemberIdResponseDto(member);
    }

    @Transactional
    public MemberIdResponseDto deleteImage(Long id) {
        Member member = memberRepository.findById(id).get();
        member.updateImageUrl(null);
        memberRepository.save(member);
        return new MemberIdResponseDto(member);
    }

    @Transactional
    public MemberIdResponseDto updateImageUrl(Long id, MultipartFile file) throws IOException {
        Member member = memberRepository.findById(id).get();
        String fileName = file.getOriginalFilename() + "." + member.getName();
        String fileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
        putBucket(file, fileName);
        member.updateImageUrl(fileUrl);
        memberRepository.save(member);
        return new MemberIdResponseDto(member);
    }

    private void putBucket(MultipartFile file, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
    }

    @Transactional
    public MemberIdResponseDto updateImageUrl(Long id, ImageUrlUpdateRequest request) {
        Member member = memberRepository.findById(id).get();
        member.updateImageUrl(request.getImageUrl());
        memberRepository.save(member);
        return new MemberIdResponseDto(member);
    }

    public List<MemberResponseDto> findAll() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();

        for (Member member : memberList) {
            MemberResponseDto dto = new MemberResponseDto(member);
            memberResponseDtos.add(dto);
        }
        return memberResponseDtos;
    }
}
