package org.sports.facility.center.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.sports.facility.center.enumconstant.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Value("${spring.mail.username}")
    private String email;


    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent, String verificationUrl) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(email);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true = HTML

        mailSender.send(message);
    }

    private String buildBookingEmailTemplate(String userName, String facilityName, String bookingDate,
                                             String startTime, String endTime,
                                             String bookingType) {

        int currentYear = java.time.Year.now().getValue();

        return """
            <html>
            <body style="font-family: 'Segoe UI', Arial, sans-serif; background-color: #f9f9f9; padding: 20px;">
                <div style="max-width:600px; margin:auto; background:#fff; padding:25px; border-radius:10px;">
                    <div style="background-color:#1976d2; color:#fff; padding:15px; text-align:center; border-radius:8px 8px 0 0;">
                        <h2>Booking Confirmed!</h2>
                    </div>
                    <div style="margin-top:20px;">
                        <p>Dear <strong>%s</strong>,</p>
                        <p>Thank you for booking with <strong>Sports Facility Center</strong>! Your reservation has been confirmed.</p>
                        <div style="background:#f2f8ff; padding:15px; border-radius:8px; margin-top:10px;">
                            <p><strong>Facility:</strong> %s</p>
                            <p><strong>Date:</strong> %s</p>
                            <p><strong>Time Slot:</strong> %s - %s</p>
                            <p><strong>Booking Type:</strong> %s</p>
                        </div>
                        <p>Please arrive at least <strong>10 minutes early</strong> before your slot begins.</p>
                        <p style="margin-top:20px;">We look forward to seeing you! 🏆</p>
                    </div>
                    <div style="text-align:center; font-size:14px; color:#666; margin-top:20px;">
                        Sports Facility Center<br/>© %d All rights reserved.
                    </div>
                </div>
            </body>
            </html>
            """.formatted(userName, facilityName, bookingDate, startTime, endTime, bookingType, currentYear);
    }

    private String buildHalfBookedEmailTemplate(String userName, String facilityName, String bookingDate,
                                                String startTime, String endTime, String bookingType) {

        int currentYear = java.time.Year.now().getValue();

        return """
            <html>
            <body style="font-family: 'Segoe UI', Arial, sans-serif; background-color: #f9f9f9; padding: 20px;">
                <div style="max-width:600px; margin:auto; background:#fff; padding:25px; border-radius:10px;">
                    <div style="background-color:#ffa000; color:#fff; padding:15px; text-align:center; border-radius:8px 8px 0 0;">
                        <h2>Half Booked - Pending Confirmation</h2>
                    </div>
                    <div style="margin-top:20px;">
                        <p>Dear <strong>%s</strong>,</p>
                        <p>Your booking request with <strong>Sports Facility Center</strong> has been <strong>partially confirmed</strong>.</p>
                        <div style="background:#fff8e1; padding:15px; border-radius:8px; margin-top:10px;">
                            <p><strong>Facility:</strong> %s</p>
                            <p><strong>Date:</strong> %s</p>
                            <p><strong>Time Slot:</strong> %s - %s</p>
                            <p><strong>Booking Type:</strong> %s</p>
                        </div>
                               <p style="margin-top:15px;">
                                           Currently, your booking is <strong>half booked</strong>, meaning the slot is temporarily reserved for your group.
                                       </p>

                                       <p>
                                           If another interested group also makes a half booking request for the same time and fulfills the remaining availability of the facility slot before the start time, your reservation will be
                                           <strong>confirmed automatically</strong>.
                                       </p>

                                       <p>
                                           However, if another group makes a booking of the facility before your confirmation,
                                           <strong>your current booking will be automatically cancelled</strong> to accommodate the full booking request.
                                       </p>

                                       <p style="margin-top:15px;">We will notify you immediately if the status of your booking changes.</p>
                        <p>Please stay tuned for updates on your booking status.</p>
                        <p style="margin-top:20px;">Thank you for your understanding and cooperation!</p>
                    </div>
                    <div style="text-align:center; font-size:14px; color:#666; margin-top:20px;">
                        Sports Facility Center<br/>© %d All rights reserved.
                    </div>
                </div>
            </body>
            </html>
            """.formatted(userName, facilityName, bookingDate, startTime, endTime, bookingType, currentYear);
    }

    @Override
    public void sendBookingConfirmationEmail(String toEmail,
                                             String userName,
                                             String facilityName,
                                             String bookingDate,
                                             String startTime,
                                             String endTime,
                                             String bookingType) throws MessagingException {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(email);
            helper.setTo(toEmail);
            helper.setSubject("✅ Booking Confirmation – Sports Facility Center");
            String htmlContent;
            if (bookingType.equalsIgnoreCase(BookingStatus.BOOKED.toString())) {
                htmlContent = buildBookingEmailTemplate(
                    userName, facilityName, bookingDate, startTime, endTime, bookingType);
            } else {
                htmlContent = buildHalfBookedEmailTemplate(userName, facilityName,
                    bookingDate, startTime, endTime, bookingType);
            }
            helper.setText(htmlContent, true); // 'true' means it's HTML
            mailSender.send(message);

            System.out.println("✅ Booking confirmation email sent to " + toEmail);

        } catch (MessagingException e) {
            log.error("❌ Failed to send booking email: ", e);
        }
    }

    private String buildCancellationEmailTemplate(String userName, String facilityName,
                                                  String bookingDate, String startTime,
                                                  String endTime, String bookingType,
                                                  boolean isUserCancelled) {
        int currentYear = java.time.Year.now().getValue();

        String message = isUserCancelled
            ? "Your booking for the facility has been cancelled as per your request."
            : "Someone else has booked this facility, and your booking has been cancelled.";

        return """
        <html>
        <body style="font-family: 'Segoe UI', Arial, sans-serif; background-color: #f9f9f9; margin: 0; padding: 20px;">
            <div style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 10px;
                        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); overflow: hidden;">
                <div style="background-color: #d9534f; color: #ffffff; padding: 15px 20px; text-align: center;">
                    <h2 style="margin: 0;">Facility Booking Cancelled</h2>
                </div>
                <div style="padding: 20px; color: #333333;">
                    <p>Dear <strong>%s</strong>,</p>
                    <p>%s</p>
                    <p>
                        <strong>Facility:</strong> %s<br>
                        <strong>Booking Type:</strong> %s<br>
                        <strong>Date:</strong> %s<br>
                        <strong>Time:</strong> %s - %s
                    </p>
                    <p>If you have any questions, please contact the facility administrator.</p>
                    <p style="margin-top: 30px;">Best regards,<br>
                    <strong>Facility Booking Team</strong></p>
                </div>
                <div style="background-color: #f0f0f0; text-align: center; padding: 10px; font-size: 12px; color: #888888;">
                    © %d Facility Booking System. All rights reserved.
                </div>
            </div>
        </body>
        </html>
        """.formatted(userName, message, facilityName, bookingType, bookingDate, startTime, endTime, currentYear);
    }

    @Override
    public void sendCancellationEmail(String toEmail,
                                      String userName,
                                      String facilityName,
                                      String bookingDate,
                                      String startTime,
                                      String endTime,
                                      boolean isUserCancelled,
                                      String bookingType) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(email);
            helper.setTo(toEmail);
            helper.setSubject("✅ Booking Cancelled – Sports Facility Center");
            String htmlContent=buildCancellationEmailTemplate(userName,
                facilityName, bookingDate, startTime, endTime, bookingType, isUserCancelled);
            helper.setText(htmlContent, true); // 'true' means it's HTML
            mailSender.send(message);
            System.out.println("✅ Booking Cancelled email sent to " + toEmail);

        } catch (Exception exception) {
            log.error("sendCancellationEmail()", exception);
        }

    }
}
