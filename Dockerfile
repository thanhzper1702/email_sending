FROM mysql:8.0

# Thiết lập thư mục dữ liệu mặc định
ENV MYSQL_DATA_DIR=/var/lib/mysql

EXPOSE 3306