package org.jeecg.modules.system.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:吴金才
 * @Date:2024/2/5 10:03
 */
@Configuration
public class MinoConfig {
    @Value("${jeecg.minio.endpoint}")
    private String endpoint;

    @Value("${jeecg.minio.port}")
    private int port;

    @Value("${jeecg.minio.publicEndpoint}")
    private String publicEndpoint;

    @Value("${jeecg.minio.accessKey}")
    private String accessKey;

    @Value("${jeecg.minio.secretKey}")
    private String secretKey;


    @Bean(name = "IMinioClient")
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(publicEndpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}