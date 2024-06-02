package org.example.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class BackendApplication {

    private static final Logger logger = LoggerFactory.getLogger(BackendApplication.class);

    @Value("${spring.datasource.url}")
    private static String datasourceUrl;

    @Value("${spring.datasource.username}")
    private static String datasourceUsername;

    @Value("${spring.datasource.password}")
    private static String datasourcePassword;

    @Value("${minio.endpoint}")
    private static String minioEndpoint;

    @Value("${minio.accesskey}")
    private static String minioAccessKey;

    @Value("${minio.secretkey}")
    private static String minioSecretKey;

    @Value("${minio.postservice.bucket.name}")
    private static String minioPostBucketName;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BackendApplication.class, args);
        logProperties(context);
    }

    private static void logProperties(ApplicationContext context) {
        datasourceUrl = context.getEnvironment().getProperty("spring.datasource.url");
        datasourceUsername = context.getEnvironment().getProperty("spring.datasource.username");
        datasourcePassword = context.getEnvironment().getProperty("spring.datasource.password");
        minioEndpoint = context.getEnvironment().getProperty("minio.endpoint");
        minioAccessKey = context.getEnvironment().getProperty("minio.accesskey");
        minioSecretKey = context.getEnvironment().getProperty("minio.secretkey");
        minioPostBucketName = context.getEnvironment().getProperty("minio.postservice.bucket.name");

        logger.info("Environment variable mappings:");
        logger.info("SPRING_DATASOURCE_URL -> spring.datasource.url -> {}", datasourceUrl);
        logger.info("SPRING_DATASOURCE_USERNAME -> spring.datasource.username -> {}", datasourceUsername);
        logger.info("SPRING_DATASOURCE_PASSWORD -> spring.datasource.password -> {}", datasourcePassword);
        logger.info("MINIO_ENDPOINT -> minio.endpoint -> {}", minioEndpoint);
        logger.info("MINIO_ACCESSKEY -> minio.accesskey -> {}", minioAccessKey);
        logger.info("MINIO_SECRETKEY -> minio.secretkey -> {}", minioSecretKey);
        logger.info("MINIO_POST_BUCKET_NAME -> minio.postservice.bucket.name -> {}", minioPostBucketName);
    }
}
