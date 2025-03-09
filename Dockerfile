FROM openjdk:17-jdk-slim
WORKDIR /oci-react-samples
COPY /target/*.jar oci-react-samples.jar
COPY /src/main/resources/Wallet_MTDRDB /oci-react-samples/Wallet_MTDRDB
ENV TNS_ADMIN=/oci-react-samples/Wallet_MTDRDB
EXPOSE 8081
ENTRYPOINT [ "java", "-jar", "oci-react-samples.jar" ] 