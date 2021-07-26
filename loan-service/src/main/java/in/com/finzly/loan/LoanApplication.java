package in.com.finzly.loan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This class provides an entry point for the
 *         loan service Application.
 */
@SpringBootApplication
@Slf4j
@EnableScheduling
@ComponentScan(basePackages = "in.com.finzly.loan")
@EnableJpaRepositories("in.com.finzly.loan.repo")
@OpenAPIDefinition(
        info = @Info(
                title = "Finzly loan services REST API",
                version = "1.0",
                termsOfService = "#",
                description = "API for Loan Model"))

public class LoanApplication {

        /**
         * Main entry point.
         *
         * @param args Array of command line arguments
         */
        public static void main(String[] args) {
        SpringApplication.run(LoanApplication.class, args);
        log.info("Application Initialized");
    }



}
