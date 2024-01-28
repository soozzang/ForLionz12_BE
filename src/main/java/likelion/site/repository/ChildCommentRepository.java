package likelion.site.repository;

import likelion.site.domain.ChildComment;
import likelion.site.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildCommentRepository extends JpaRepository<ChildComment, Long> {
    List<ChildComment> findByComment(Comment comment);
}
