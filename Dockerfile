# Используем официальный образ Tomcat
FROM tomcat:latest

# Устанавливаем рабочую директорию
WORKDIR /usr/local/tomcat

# Копируем ваше WAR-приложение в веб-аппс директорию Tomcat
#COPY target/RestTemplate-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
#COPY conf/server.xml /usr/local/tomcat/conf/server.xml

# Открываем порт 8080 для доступа к приложению
EXPOSE 8080

# Запускаем Tomcat
CMD ["catalina.sh", "run"]