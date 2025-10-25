package org.sports.facility.center.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

    void sendHtmlEmail(String to, String subject, String htmlContent, String token) throws MessagingException;

    void sendBookingConfirmationEmail(String toEmail, String userName,
                                      String facilityName, String bookingDate,
                                      String startTime, String endTime,
                                      String bookingType)  throws MessagingException;



}
