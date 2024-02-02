package likelion.site.domain.assignment.repository;

import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.AssignmentPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByAssignmentPart(AssignmentPart assignmentPart);

}
