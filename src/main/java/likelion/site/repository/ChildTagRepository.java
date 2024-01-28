package likelion.site.repository;

import likelion.site.domain.ChildComment;
import likelion.site.domain.ChildTag;
import likelion.site.domain.ParentTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildTagRepository extends JpaRepository<ChildTag, Long> {
    List<ChildTag> findByParenttag(ParentTag parentTag);
}
