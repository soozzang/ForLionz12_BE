package likelion.site.domain.assignment.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.Submission;
import likelion.site.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {


    public Submission findSubmissionByAssignmentAndMember(Assignment assignment , Member member);
}
