package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.repository.QuestionPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionPostService {

    private final QuestionPostRepository questionPostRepository;

    @Transactional
    public Long addQuestionPost(QuestionPost questionPost){
        questionPostRepository.save(questionPost);
        return questionPost.getId();
    }

    public List<QuestionPost> findAllQuestionPosts() {
        return questionPostRepository.findAll();
    }

    public QuestionPost findQuestionPostById(Long questionPostId) {
        return questionPostRepository.findById(questionPostId);
    }

    @Transactional
    public void update(Long id, String title, String content) {
        QuestionPost questionPost = questionPostRepository.findById(id);
        questionPost.updateQuestionPost(title,content);
        addQuestionPost(questionPost);
    }

    @Transactional
    public void delete(Long id) {
        questionPostRepository.delete(id);
    }


}
