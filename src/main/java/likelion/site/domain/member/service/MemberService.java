package likelion.site.domain.member.service;

import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.dto.MemberResponseDto;
import likelion.site.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public MemberResponseDto findMemberInfoById(Long memberId) {
        Member member =  memberRepository.findById(memberId).get();
        return new MemberResponseDto(member);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public Long updateIntroduction(Long id, String introduction) {
        Member member = findMemberById(id).get();
        member.updateIntroduction(introduction);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long updateGithubAddress(Long id, String githubAddress) {
        Member member = findMemberById(id).get();
        member.updateGithubAddress(githubAddress);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long updatePassword(Long id, String password) {
        Member member = findMemberById(id).get();
        member.updatePassword(password);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long updateInstagramId(Long id, String instagramId) {
        Member member = findMemberById(id).get();
        member.updateInstagramId(instagramId);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long updateImageUrl(Long id, String imageUrl) {
        Member member = findMemberById(id).get();
        member.updateImageUrl(imageUrl);
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
