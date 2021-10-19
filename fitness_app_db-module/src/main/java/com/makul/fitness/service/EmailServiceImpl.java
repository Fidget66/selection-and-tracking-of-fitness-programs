package com.makul.fitness.service;

import com.makul.fitness.model.ExerciseSchedule;
import com.makul.fitness.service.api.EmailService;
import com.makul.fitness.service.api.ExerciseScheduleService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final ExerciseScheduleService service;
    private final SimpleMailMessage message;

    public EmailServiceImpl(JavaMailSender emailSender, ExerciseScheduleService service, SimpleMailMessage message) {
        this.emailSender = emailSender;
        this.service = service;
        this.message = message;
    }

    @Override
    @Scheduled(cron = "0 15 1 * * *")
    public void sendSimpleMessage() {
        String subject = "Напоминание о занятиях";
        LocalDate dateOfExercise = LocalDate.now().plusDays(1);
        List<ExerciseSchedule> exerciseList= service.readExerciseByDate(dateOfExercise);
        for (ExerciseSchedule exercise:exerciseList) {
            String text = "Здравствуйте, "+ exercise.getActiveProgram().getUser().getFirstName()+", напоминаем, что " +
                    "завтра " + dateOfExercise + " у вас занятие";
            String toEmail = exercise.getActiveProgram().getUser().getEmail();
            message.setFrom("myfitnessappmail@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        }
    }
}
