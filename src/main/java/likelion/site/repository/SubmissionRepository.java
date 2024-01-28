package likelion.site.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion.site.domain.Assignment;
import likelion.site.domain.Submission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SubmissionRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Submission submission){
        em.persist(submission);
    }

    public Submission findById(Long id) {
        return em.find(Submission.class, id);
    }
}
