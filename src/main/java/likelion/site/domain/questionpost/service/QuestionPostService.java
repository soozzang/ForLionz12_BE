package likelion.site.domain.questionpost.service;

import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.repository.QuestionPostRepository;
import likelion.site.global.exception.exceptions.BadElementException;
import likelion.site.global.exception.CustomError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<QuestionPost> questionPost = questionPostRepository.findById(questionPostId);
        if (questionPost.isPresent()) {
            return questionPost.get();
        }
        throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
    }

    @Transactional
    public void update(Long id, String title, String content) {
        Optional<QuestionPost> questionPost = questionPostRepository.findById(id);
        if (questionPost.isPresent()) {
            questionPost.get().updateQuestionPost(title,content);
            addQuestionPost(questionPost.get());
        }
        throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
    }

    @Transactional
    public void delete(QuestionPost questionPost) {
        questionPostRepository.delete(questionPost);
    }

}
