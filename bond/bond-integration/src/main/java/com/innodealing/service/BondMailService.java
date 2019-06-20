package com.innodealing.service;

import com.innodealing.bond.param.BondMailReq;
import com.innodealing.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class BondMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BondMailService.class);

    private @Autowired
    JavaMailSender mailSender;

    public String sendText(BondMailReq req) {
        String mailTo = req.getTo();
        String mailFrom = StringUtils.isBlank(req.getFrom()) ? "no-reply@innodealing.com" : req.getFrom();
        String subject = req.getSubject();
        String content = req.getContent();
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(mailTo);
            message.setSubject(subject);
//            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
//                    "favoriteGroupEmailTemplate.vm", "UTF-8", groupEmailVO.getFavoriteMap());
            message.setText(content);
            mailSender.send(message);
            LOGGER.info(String.format("sendText: sent email from [%1$s] with subject[%2$s]",
                    mailFrom, subject));
        } catch (Exception ex) {
            LOGGER.error("sendText error:" + ex.getMessage());
            return "failed";
        }
        return "done";
    }

    public String sendHtmlByTemplate(BondMailReq req) {
        String mailTo = req.getTo();
        String mailFrom = StringUtils.isBlank(req.getFrom()) ? "no-reply@innodealing.com" : req.getFrom();
        String subject = req.getSubject();
        String content = req.getContent();
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setFrom(mailFrom);
            helper.setTo(mailTo);
            helper.setSubject(subject);
//            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
//                    "favoriteGroupEmailTemplate.vm", "UTF-8", groupEmailVO.getFavoriteMap());
            helper.setText(content, true);
            mailSender.send(mail);
            LOGGER.info(String.format("sendHtmlByTemplate: sent email from [%1$s] with subject[%2$s]",
                    mailFrom, subject));
        } catch (Exception ex) {
            LOGGER.error("sendHtmlByTemplate error:" + ex.getMessage());
            return "failed";
        }
        return "done";
    }
}
