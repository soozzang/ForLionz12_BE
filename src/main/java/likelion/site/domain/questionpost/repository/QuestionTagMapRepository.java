package likelion.site.domain.questionpost.repository;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionTagMapRepository extends JpaRepository<QuestionTagMap, Long> {
    List<QuestionTagMap> findByChildTag(ChildTag childTag);

}
