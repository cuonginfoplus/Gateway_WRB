package gateway.wrb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BankConfig {
    @Value("${bank.code}")
    private String bankCode;

    @Value("${oranization.code}")
    private String orgCode;

}
