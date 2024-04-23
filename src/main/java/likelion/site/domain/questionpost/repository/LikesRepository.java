package likelion.site.domain.questionpost.repository;

import likelion.site.domain.member.domain.Member;
import likelion.site.domain.questionpost.domain.Likes;
import likelion.site.domain.questionpost.domain.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    public Optional<Likes> findByQuestionPostAndMember(QuestionPost questionPost, Member member);

    public void deleteLikesById(Long likesId);
}
