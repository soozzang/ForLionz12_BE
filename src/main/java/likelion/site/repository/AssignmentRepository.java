package likelion.site.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion.site.domain.Assignment;
import likelion.site.domain.AssignmentPart;
import likelion.site.domain.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByAssignmentPart(AssignmentPart assignmentPart);

}
