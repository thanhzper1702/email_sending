package murach.email.controller;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import murach.email.model.User;
import murach.email.dao.UserDAO;
import murach.email.util.MailUtilGmail;

@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy action hiện tại
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";
        }

        String url = "/index.jsp";
        if (action.equals("join")) {
            url = "/index.jsp";
        } else if (action.equals("add")) {
            // 2. Lấy dữ liệu từ form
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            // 3. Lưu user vào PostgreSQL qua JPA
            User user = new User(firstName, lastName, email);
            UserDAO.insert(user);
            request.setAttribute("user", user);

            // 4. Chuẩn bị nội dung gửi mail (Tiêu đề và câu chữ mang tính thông báo cá nhân)
            String to = email;
            String from = "thanh17022006@gmail.com"; 
            String subject = "Xác nhận đăng ký thông tin tài khoản";
            String body = "Chào " + firstName + ",\n\n" +
                    "Hệ thống xác nhận bạn đã đăng ký thông tin thành công.\n" +
                    "Thông tin tài khoản của bạn đã được lưu trữ trên cơ sở dữ liệu.\n\n" +
                    "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua thư này.\n\n" +
                    "Trân trọng,\n" +
                    "Bộ phận hỗ trợ kỹ thuật";
            boolean isBodyHTML = false;

            // 5. Gửi mail qua Brevo REST API
            try {
                MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
            } catch (Exception e) {
                String errorMessage = "ERROR: Unable to send email. " +
                        "Check Tomcat logs for details.<br>" +
                        "ERROR MESSAGE: " + e.getMessage();
                request.setAttribute("errorMessage", errorMessage);
                this.log("Lỗi gửi mail: " + e.getMessage(), e);
            }
            url = "/thanks.jsp";
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}