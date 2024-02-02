package likelion.site.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion.site.domain.Assignment;
import likelion.site.domain.AssignmentPart;
import likelion.site.domain.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AssignmentRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Assignment assignment){
        em.persist(assignment);
    }

    public Assignment findById(Long id) {
        return em.find(Assignment.class, id);
    }

    public List<Assignment> findAll() {
        return em.createQuery("select n from Assignment n", Assignment.class)
                .getResultList();
    }

    public List<Assignment> findByAssignmentAssignmentPart(AssignmentPart assignmentPart) {
        return em.createQuery("select n from Assignment n where n.assignmentPart=:assignmentPart",Assignment.class)
                .setParameter("assignmentPart",assignmentPart)
                .getResultList();
    }

    public void delete(Long id) {
        em.createQuery(
                        "delete from Assignment a where a.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
