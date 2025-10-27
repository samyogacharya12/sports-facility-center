package org.sports.facility.center.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;



@Configuration
public class GoogleCloudStorageConfig {

    @Value("${gcp.project.id}")
    private String projectId;

    @Value("${gcp.credentials.path}")
    private String credentialsPath;

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public Storage storage() throws IOException {
        Resource resource = resourceLoader.getResource(credentialsPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        return StorageOptions.newBuilder()
            .setCredentials(credentials)
            .setProjectId(projectId)
            .build()
            .getService();
    }
}
