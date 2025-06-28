package net.api.srpring_kurses.controller;

import net.api.srpring_kurses.dto.test.AnswerResultsDTO;
import net.api.srpring_kurses.dto.test.TestDTO;
import net.api.srpring_kurses.model.Test;
import net.api.srpring_kurses.requests_models.UserAnswerInformationREQ;
import net.api.srpring_kurses.service.JWTService;
import net.api.srpring_kurses.service.PracticeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/practice")
public class PracticeController {

    private final JWTService jwtService;
    private final PracticeService practiceService;

    public PracticeController(JWTService jwtService, PracticeService practiceService) {
        this.jwtService = jwtService;
        this.practiceService = practiceService;
    }

    @GetMapping("/information")
    public TestDTO getInformation (
            @RequestParam Long courseId,
            @RequestParam Long sectionId
    ) {
        Test practiceTest = practiceService.findByCourseIdAndSectionId(courseId, sectionId);
        if (practiceTest == null) {
            return null;
        }
        return new TestDTO(practiceTest);
    }

    @PostMapping("/check")
    public AnswerResultsDTO checkAnswer(
            @RequestBody UserAnswerInformationREQ userAnswersREQ,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        return practiceService.checkCorrectAnswer(userAnswersREQ, jwtService.decodeTokenGetId(authorizationHeader));
    }

}
