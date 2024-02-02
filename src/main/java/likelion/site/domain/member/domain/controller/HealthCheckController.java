package likelion.site.domain.member.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/health")
@RestController
class HealthCheckController {

    @GetMapping
    public String healthCheck() {
        return "lionz server ok!";
    }
}