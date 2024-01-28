package likelion.site.service;

import likelion.site.domain.Assignment;
import likelion.site.domain.Notification;
import likelion.site.domain.Part;
import likelion.site.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Transactional
    public Long addAssignment(Assignment assignment){
        assignmentRepository.save(assignment);
        return assignment.getId();
    }

    public List<Assignment> findAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment findAssignmentById(Long assignmentId) {
        return assignmentRepository.findById(assignmentId);
    }

    public List<Assignment> findAssignmentByPart(Part part) {
        return assignmentRepository.findByPart(part);
    }


    @Transactional
    public void updateAssignment(Long id, String title, String content, Part part, LocalDateTime expireAt, List<String> tags) {
        Assignment assignment = assignmentRepository.findById(id);
        assignment.updateAssignment(title,content,part,expireAt,tags);
        addAssignment(assignment);
    }

    @Transactional
    public void delete(Long id) {
        assignmentRepository.delete(id);
    }
}
