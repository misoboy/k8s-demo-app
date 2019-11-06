FROM tomcat:latest
MAINTAINER ChiSeok, Song <misoboy.kor@gmail.com

# Application Deploy
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/k8s-demo-app-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/demo.war

CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
expose 8080