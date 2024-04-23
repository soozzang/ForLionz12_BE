package likelion.site.domain.questionpost.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.questionpost.domain.QuestionPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionPostRepository extends JpaRepository<QuestionPost, Long> {
}
