package net.api.srpring_kurses.service;

import net.api.srpring_kurses.dto.test.AnswerResultsDTO;
import net.api.srpring_kurses.model.StudentSectionProgress;
import net.api.srpring_kurses.model.Test;
import net.api.srpring_kurses.model.TestAnswer;
import net.api.srpring_kurses.model.TestQuestion;
import net.api.srpring_kurses.repo.CourseSectionRepo;
import net.api.srpring_kurses.repo.StudentSectionProgressRepo;
import net.api.srpring_kurses.repo.TestRepo;
import net.api.srpring_kurses.repo.UserRepo;
import net.api.srpring_kurses.requests_models.UserAnswerInformationREQ;
import net.api.srpring_kurses.requests_models.UserAnswerREQ;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@Service
public class PracticeService {
    private final TestRepo testRepo;
    private final StudentSectionProgressRepo studentSectionProgressRepo;
    private final CourseSectionRepo courseSectionRepo;
    private final UserRepo userRepo;
    private final StudentProgressService studentProgressService;

    public PracticeService(TestRepo testRepo, StudentSectionProgressRepo studentSectionProgressRepo, CourseSectionRepo courseSectionRepo, UserRepo userRepo, StudentProgressService studentProgressService) {
        this.testRepo = testRepo;
        this.studentSectionProgressRepo = studentSectionProgressRepo;
        this.courseSectionRepo = courseSectionRepo;
        this.userRepo = userRepo;
        this.studentProgressService = studentProgressService;
    }

    public Test findByCourseIdAndSectionId(Long courseId, Long sectionId) {
        return testRepo.findByCourseIdAndSectionId(courseId, sectionId);
    }


    @Transactional
    public AnswerResultsDTO checkCorrectAnswer(UserAnswerInformationREQ answerReq, Long userId) {
        AnswerResultsDTO answerResultsDTO = getCorrectAnswer(answerReq);
        StudentSectionProgress studentSectionProgress = new StudentSectionProgress(answerReq);
        BigDecimal scorePercent = valueOf(answerResultsDTO.getQuantityCorrectAnswer())
                .divide(valueOf(answerResultsDTO.getTotalQuestions()), 4, HALF_UP)
                .multiply(valueOf(100))
                .setScale(2, HALF_UP);
        studentSectionProgress.setScorePercent(scorePercent);

        studentSectionProgress.setCompleted(scorePercent.compareTo(valueOf(40)) >= 0);
        studentSectionProgress.setSection(courseSectionRepo.findSectionCourses(answerReq.getCourseId(), answerReq.getSectionId()));
        studentSectionProgress.setStudent(userRepo.findByIdDirect(userId));
        studentSectionProgressRepo.save(studentSectionProgress);
        studentProgressService.updateStudentCourseProgress(answerReq.getCourseId(), userId);
        return answerResultsDTO;
    }

    private AnswerResultsDTO getCorrectAnswer(UserAnswerInformationREQ answerReq) {
        Test test = testRepo.findBySectionIdAndCourseId(answerReq.getCourseId(), answerReq.getSectionId());

        Map<Long, String> correctAnswersMap = test.getQuestions().stream()
                .collect(Collectors.toMap(
                        TestQuestion::getId,
                        q -> q.getAnswers().stream()
                                .filter(TestAnswer::isCorrect)
                                .findFirst()
                                .map(TestAnswer::getAnswerText)
                                .orElse(null)
                ));

        List<Long> incorrectAnswerIds = new ArrayList<>();
        long correctCount = 0;

        for (UserAnswerREQ userAnswer : answerReq.getAnswers()) {
            String correctAnswer = correctAnswersMap.get(userAnswer.getId());
            if (correctAnswer != null && correctAnswer.equals(userAnswer.getCurrentAnswer())) {
                correctCount++;
            } else {
                incorrectAnswerIds.add(userAnswer.getId());
            }
        }

        AnswerResultsDTO result = new AnswerResultsDTO();
        result.setQuantityCorrectAnswer(correctCount);
        result.setTotalQuestions((long) test.getQuestions().size());
        result.setIncorrectAnswerId(incorrectAnswerIds);

        return result;
    }



}
