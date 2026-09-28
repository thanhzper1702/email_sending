package murach.email.util;

import sibApi.TransactionalEmailsApi;
import sibModel.*;
import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import java.util.Collections;

public class MailUtilGmail {

    // Dán API Key Brevo của bạn vào đây
    private static final String BREVO_API_KEY = "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";

    public static void sendMail(String to, String from,
            String subject, String body, boolean bodyIsHTML) {

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(BREVO_API_KEY);

        TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();

        // Người gửi: Bắt buộc là Gmail đã verify trên Brevo
        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(from);
        sender.setName("Chí Thành Web");

        // Người nhận: Bất kỳ email nào
        SendSmtpEmailTo recipient = new SendSmtpEmailTo();
        recipient.setEmail(to);

        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(Collections.singletonList(recipient));
        sendSmtpEmail.setSubject(subject);

        if (bodyIsHTML) {
            sendSmtpEmail.setHtmlContent(body);
        } else {
            sendSmtpEmail.setTextContent(body);
        }

        try {
            CreateSmtpEmail result = apiInstance.sendTransacEmail(sendSmtpEmail);
            System.out.println("Gửi mail thành công! Message ID: " + result.getMessageId());
        } catch (Exception e) {
            System.err.println("Gửi mail thất bại: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi gửi mail: " + e.getMessage(), e);
        }
    }
}