package test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Đang kết nối đến PostgreSQL trên Render...");
        try {
            // Tên "emailListPU" phải khớp chính xác với name trong persistence.xml
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("emailListPU");
            EntityManager em = emf.createEntityManager();

            System.out.println(">>> KẾT NỐI THÀNH CÔNG VỚI RENDER POSTGRESQL! <<<");

            // Đóng kết nối
            em.close();
            emf.close();
        } catch (Exception e) {
            System.err.println(">>> KẾT NỐI THẤT BẠI! <<<");
            e.printStackTrace();
        }
    }
}