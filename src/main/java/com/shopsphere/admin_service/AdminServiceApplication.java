package com.shopsphere.admin_service;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
        info = @Info(
                title = "Admin Service REST API Documentation",
                version = "v1",
                description = "ShopSphere service for perform admin operations across all other services",
                contact = @Contact(
                        name = "Yasura Laksitha",
                        email = "yasura.dev@gmail.com",
                        url = "https://github.com/YasuraLaksitha/shopsphere-admin-service.git"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Postman collection for admin API operations",
                url = "https://github.com/YasuraLaksitha/shopsphere-admin-service.git"
        )
)
public class AdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }

}
