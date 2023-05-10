package com.example.course_work.Utils;

import com.example.course_work.Entity.User;
import com.example.course_work.Service.EmailService;
import com.example.course_work.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SheduledService {
    private final UserService userService;
    private final EmailService emailService;

    /**
     * Настраиваемая задача для поздравления пользователей, у которых сегодня день рождения
     * Она выполняется каждый день в 01:00:00
     */
    @Scheduled(cron="0 0 1 * * *")
    public void greetUser() {
        Date today = new Date();
        List<User> users = userService.getBirthdayUsers(today.getMonth(), today.getDay());
        for (User user : users) {
            String subject = "Поздравление";
            String text =  String.format("%s %s! Команда магазина SweetCandy.com поздравляет Вас с Днем Рождения"
                    + " и дарит скидку 20%% на первую покупук в нашем магазине в течении следующей недели",
                    user.getFirstname(), user.getSecondname());
            emailService.sendEmail(subject, text, user.getEmail());
        }
    }
}
