package animelord.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailUtility {

    private static final String EMAIL =
            "yahiyasiddiqui08@gmail.com";

    private static final String APP_PASSWORD =
            "avumzkwiobzoykhk";

    public static void sendVerificationEmail(
            String recipient,
            String verificationLink)
            throws Exception {

        Properties props =
                new Properties();

        props.put(
                "mail.smtp.auth",
                "true"
        );

        props.put(
                "mail.smtp.starttls.enable",
                "true"
        );

        props.put(
                "mail.smtp.host",
                "smtp.gmail.com"
        );

        props.put(
                "mail.smtp.port",
                "587"
        );

        Session session =
                Session.getInstance(
                        props,
                        new Authenticator() {

                            @Override
                            protected PasswordAuthentication
                            getPasswordAuthentication() {

                                return new PasswordAuthentication(
                                        EMAIL,
                                        APP_PASSWORD
                                );
                            }
                        }
                );

        Message message =
                new MimeMessage(session);

        message.setFrom(
                new InternetAddress(EMAIL)
        );

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipient)
        );

        message.setSubject(
                "AnimeLord Email Verification"
        );

        message.setText(
                "Verify your account:\n\n"
                + verificationLink
        );

        Transport.send(message);
    }
}