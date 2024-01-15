package likelion.site.service;

import likelion.site.domain.User;
import likelion.site.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저 추가
     */
    @Transactional
    public Long addUser(User user) {
        userRepository.save(user);
        return user.getId();
    }

    /**
     * 전체 유저 조회
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId);
    }
}
