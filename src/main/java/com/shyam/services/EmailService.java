package com.shyam.services;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shyam.entities.UserEntity;
import com.shyam.repositories.UserRepository;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final HttpServletRequest request;
    private final Configuration configuration;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    public void sendActivationEmail(UserEntity user) {
        System.out.println("Sending activation mail.........");

        user.setUniqueToken(UUID.randomUUID().toString());
        user.setExpirationTime(LocalDateTime.now().plusHours(1));
        userRepository.save(user);
        
        HashMap<String, Object> map = new HashMap<>();
        Writer out = new StringWriter();

        String link = request.getRequestURL().toString().replace(request.getServletPath(), "");
        link = link + "/auth/activate-user/"+user.getUniqueToken();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom("vitap.library@gmail.com", "Admin");
            helper.setTo(user.getEmail());
            helper.setSubject("User activation email");

            Template template = configuration.getTemplate("activate.ftl");

            map.put("userName", user.getName());
            map.put("activationLink", link);
            template.process(map, out);

            helper.setText(out.toString(), true);

            javaMailSender.send(mimeMessage);
        } 
        catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public void sendSetPasswordEmail(UserEntity user) {
        
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        user.setUniqueToken(UUID.randomUUID().toString());
        user.setExpirationTime(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        try {
            Writer out = new StringWriter();
            HashMap<String, Object> map = new HashMap<>();
            map.put("userName", user.getName());

            // String link = request.getRequestURL().toString().replace(request.getServletPath(), "");
            // link = link + "/auth/validate-token/"+user.getUniqueToken();
            String link = "http://localhost:5173/set-password?token="+user.getUniqueToken();
            map.put("link", link);

            helper.setFrom("vitap.library@gmail.com", "Admin");
            helper.setTo(user.getEmail());
            helper.setSubject("Set password email");

            Template template = configuration.getTemplate("set-password-email.ftl");
            template.process(map, out);

            helper.setText(out.toString(), true);

            javaMailSender.send(mimeMessage);

        } 
        catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}