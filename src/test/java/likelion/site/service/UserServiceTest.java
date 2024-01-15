package likelion.site.service;

import likelion.site.domain.Part;
import likelion.site.domain.User;
import likelion.site.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 유저추가_및_유저검색() throws Exception {
        //given
        User user = User.builder()
                .email("soozzang@github.io")
                .name("hangsoo")
                .part(Part.STAFF)
                .password("hangsoo511")
                .build();

        //when
        Long savedId = userService.addUser(user);

        //then
        assertThat(user).isEqualTo(userRepository.findById(savedId));
    }

    @Test
    public void 모든유저_검색() throws Exception {
        //given
        User user = User.builder()
                .email("soozzang@github.io")
                .name("hangsoo")
                .part(Part.STAFF)
                .password("hangsoo511")
                .build();
        User user2 = User.builder()
                .email("soozzsang@github.io")
                .name("hangsoo")
                .part(Part.STAFF)
                .password("hangsoo511")
                .build();

        //when
        userService.addUser(user);
        userService.addUser(user2);

        //then
        assertThat(2).isEqualTo(userService.findUsers().size());
    }
}