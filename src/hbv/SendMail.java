package hbv;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class SendMail {

    private String to = "recipientEmail";
    private String from = "coronaapp65@gmail.com"; 
    private String password = "ywdjntzxyllwoclk"; 
    private String host = "smtp.gmail.com";

    Properties properties = new Properties();
    {
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
    }

    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from, password);
        }
    });

    public void sendMailRegister(String recipientEmail, String lastName) {
        to = recipientEmail;

        Thread emailThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject("Einrichtung des Kontos für die Buchung von Terminen für die Covid-19-Impfung.");
                    message.setText("Hallo Frau/Herr " + lastName + "," + "\n" +"Ihr Konto wurde erfolgreich erstellt. Klicken Sie " +
                            "auf den folgenden Link um Ihnen enloggen zu können: " + "\n"
                            +"https://informatik.hs-bremerhaven.de/docker-swe3-2022team08-java/");
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
        emailThread.start();
    }

    public void sendMailConfirmationWithPdf(String recipientEmail, String firstName, String lastName, String city,
                                             String postalCode, String vaccine, LocalDate date1, LocalTime heure1,
                                             LocalDate date2, LocalTime heure2) {

        to = recipientEmail;

        // Création du document PDF
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Création d'une police pour les titres
            Font titleFont = new Font(FontFamily.HELVETICA, 16, Font.BOLD);

            // Ajout du titre principal
            Paragraph title = new Paragraph("Terminbestätigung für " + firstName + " " +lastName, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Ajout des informations utilisateur
            document.add(new Paragraph("Name: " + lastName));
            document.add(new Paragraph("Vorname: " + firstName));
            document.add(new Paragraph("Email: " + recipientEmail));
            document.add(new Paragraph("Stadt: " + city));
            document.add(new Paragraph("Postleitzahl: " + postalCode         ));

            // Ajout des informations de rendez-vous
            Paragraph appointmentHeader = new Paragraph("Termine:", titleFont);
            appointmentHeader.setSpacingAfter(20);
            document.add(appointmentHeader);

            document.add(new Paragraph("Erster Termin: " + date1 + " um " + heure1));
            document.add(new Paragraph("Zweiter Termin: " + date2 + " um " + heure2));
            document.add(new Paragraph("Impfstoff: " + vaccine));

            // Ajout d'un message de confirmation
            Paragraph confirmation = new Paragraph("Vielen Dank für Ihre Terminbuchung. Bitte bringen Sie Ihren Personalausweis  mit.");
            confirmation.setAlignment(Element.ALIGN_CENTER);
            confirmation.setSpacingBefore(20);
            document.add(confirmation);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        Thread emailThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                    message.setSubject("Terminbestätigung für " + lastName);

                    // Créer la partie corps du message
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent("Sehr geehrte/r " +"<b>"+ firstName + " " + lastName +"</b>"
                            + ",<br/><br/>anbei erhalten Sie die Terminbestätigung für Ihren Impftermin. "
                            + "Bitte bringen Sie das Dokument zum Impfzentrum mit.<br/><br/>Mit freundlichen Grüßen<br/>"
                            + "Ihr Impfzentrum", "text/html");

                    // Créer le corps de la pièce jointe
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                    attachmentBodyPart.setFileName(lastName + "Buchung.pdf");
                    attachmentBodyPart.setDataHandler(new javax.activation.DataHandler(new javax.mail.util.ByteArrayDataSource(baos.toByteArray(), "application/pdf")));

                    // Créer le message multipart et ajouter les parties du corps.
                    MimeMultipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);
                    multipart.addBodyPart(attachmentBodyPart);

                    // Définir le message multipartite comme le message e-mail
                    message.setContent(multipart);

                    Transport.send(message);

                    System.out.println("Le mail a été envoyé avec succès.");
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        emailThread.start();
    }
}
