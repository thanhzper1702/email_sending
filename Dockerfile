# Stage 1: Build file .war bằng Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# Tải dependencies trước để cache
RUN mvn dependency:go-offline -B
COPY src ./src
# Build ứng dụng (bỏ qua test để build nhanh hơn)
RUN mvn clean package -DskipTests

# Stage 2: Chạy ứng dụng trên Tomcat 10.1 (Jakarta EE 10)
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat

# Xóa các webapp mặc định của Tomcat
RUN rm -rf webapps/*

# Copy file .war vừa build vào và đổi tên thành ROOT.war để web chạy ở đường dẫn gốc /
COPY --from=build /app/target/*.war webapps/ROOT.war

# Mở cổng 8080
EXPOSE 8080

CMD ["catalina.sh", "run"]