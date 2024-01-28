package likelion.site.repository;

import likelion.site.domain.ChildComment;
import likelion.site.domain.ChildTag;
import likelion.site.domain.QuestionTagMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionTagMapRepository extends JpaRepository<QuestionTagMap, Long> {
    List<QuestionTagMap> findByChildTag(ChildTag childTag);

}
