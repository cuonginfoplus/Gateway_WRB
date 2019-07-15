package gateway.wrb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class FbkConfig {
    @Value("${path.fbk}")
    private String fbkPath;

    @Value("${path.fbkbackup}")
    private String fbkPathBackup;

    @Value("${path.file.type}")
    private String fbkType;

    @Value("${path.file.header_snd}")
    private String headerSnd;

    @Value("${path.file.header_vir}")
    private String headerVir;

}
