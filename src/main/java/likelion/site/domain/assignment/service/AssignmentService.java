package likelion.site.domain.assignment.service;

import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.AssignmentMainContent;
import likelion.site.domain.assignment.domain.AssignmentPart;
import likelion.site.domain.assignment.repository.AssignmentRepository;
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
        return assignmentRepository.findById(assignmentId).get();
    }

    public List<Assignment> findAssignmentByPart(AssignmentPart assignmentPart) {
        return assignmentRepository.findByAssignmentPart(assignmentPart);
    }


    @Transactional
    public void updateAssignment(Long id, AssignmentMainContent assignmentMainContent, String title, String content, AssignmentPart assignmentPart, LocalDateTime expireAt, List<String> tags) {
        Assignment assignment = assignmentRepository.findById(id).get();
        assignment.updateAssignment(title,content,assignmentMainContent,assignmentPart,expireAt,tags);
        addAssignment(assignment);
    }

    @Transactional
    public void delete(Long id) {
        assignmentRepository.delete(assignmentRepository.findById(id).get());
    }
}
