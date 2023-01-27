package nz.netvalue.domain.model.version;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Version information about application, that stores in application.yaml
 */
@Component
public class Version {

    @Value("${application.version.database:1.0}")
    private String database;
    @Value("${application.version.application:1.0}")
    private String application;

    public String getDatabase() {
        return database;
    }

    public String getApplication() {
        return application;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
