package com.makul.fitness.service;

import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.service.api.EmailService;
import com.makul.fitness.service.api.ExerciseScheduleSearcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final ExerciseScheduleSearcherService service;
    private final SimpleMailMessage message;
    @Value("${email.from}")
    private String fromEmail;

    @Override
    @Scheduled(cron = "${email.time}")
    public void sendSimpleMessage() {
        String subject = "Напоминание о занятиях";
        LocalDate dateOfExercise = LocalDate.now().plusDays(1);
        List<ExerciseSchedule> exerciseList = service.readExerciseByDate(dateOfExercise);
        for (ExerciseSchedule exercise : exerciseList) {
            String text = String.format("Здравствуйте, %s, напоминаем, что завтра %s у вас занятие",
                    exercise.getActiveProgram().getUser().getFirstName(), dateOfExercise);
            String toEmail = exercise.getActiveProgram().getUser().getEmail();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        }
    }
}
