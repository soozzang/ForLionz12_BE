package likelion.site.domain.questionpost.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.questionpost.domain.QuestionPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionPostRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(QuestionPost questionPost) {
        em.persist(questionPost);
    }

    public QuestionPost findById(Long id) {
        return em.find(QuestionPost.class, id);
    }

    public List<QuestionPost> findAll() {
        return em.createQuery("select q from QuestionPost q", QuestionPost.class)
                .getResultList();
    }

    /**
     *  수정 필요
     */
    public List<Assignment> findByTag(Part part) {
        return em.createQuery("select n from Assignment n where n.part=:part",Assignment.class)
                .setParameter("part",part)
                .getResultList();
    }

    public void delete(Long id) {
        em.createQuery(
                        "delete from QuestionPost q where q.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }




}
