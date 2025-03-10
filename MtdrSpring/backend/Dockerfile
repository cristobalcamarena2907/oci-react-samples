FROM openjdk:22
#COPY pom.xml /tmp/
#COPY src /tmp/src/
WORKDIR /tmp/
EXPOSE 8080
COPY target/MyTodoList-0.0.1-SNAPSHOT.jar MyTodoList.jar
ENTRYPOINT ["java","-jar","MyTodoList.jar"]

# FROM openjdk:17-jdk-slim
# WORKDIR /oci-react-samples
# COPY /target/*.jar oci-react-samples.jar
# COPY /src/main/resources/Wallet_MTDRDB /oci-react-samples/Wallet_MTDRDB
# ENV TNS_ADMIN=/oci-react-samples/Wallet_MTDRDB
# EXPOSE 8081
# ENTRYPOINT [ "java", "-jar", "oci-react-samples.jar" ] 