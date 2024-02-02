package likelion.site.domain.questionpost.repository;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildTagRepository extends JpaRepository<ChildTag, Long> {
    List<ChildTag> findByParenttag(ParentTag parentTag);
}
