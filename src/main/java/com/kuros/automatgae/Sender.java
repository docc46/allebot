package com.kuros.automatgae;

import com.kuros.automatgae.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.ImageOutputStreamImpl;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Component
public class Sender {


    private JavaMailSender emailSender = getJavaMailSender();

    public void sendMessage(String to, String subject, String text, Voucher voucher) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        System.out.println(to);
        helper.setFrom("automat@psc24.pl");
        helper.setText(text);
        helper.setSubject(subject);

        File file = new File("template.png");
        BufferedImage image = ImageIO.read(file);

        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(19f));
        g.drawString(voucher.getVoucherCode(), 91, 243);
        g.setFont(g.getFont().deriveFont(11f));
        g.drawString(voucher.getTransId(), 53, 143);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();

        g.drawString(dateFormat.format(date), 103, 129);
        g.setFont(g.getFont().deriveFont(12f));
        g.drawString("000000" + voucher.getSerialNumber(), 96, 295);
        g.drawString(voucher.getValue().replace(".00","") + " PLN", 61, 309);
        g.dispose();

        File output1 = new File("test1.png");
        ImageIO.write(image, "png", output1);

        helper.addAttachment("paysafecard.png", output1);

        emailSender.send(message);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("serwer1996102.home.pl");
        mailSender.setPort(465);

        mailSender.setUsername("automat@psc24.pl");
        mailSender.setPassword("tr@cerhere!");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}
