package com.shopsphere.admin_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(
        name = "Error Response",
        description = "Schema to hold error information"
)
public class ErrorResponseDTO {

    @Schema(description = "Status code for response")
    private String status;

    @Schema(description = "URL of the request")
    private String path;

    @Schema(description = "Reason for the error")
    private String message;

    @Schema(description = "Timestamp when the response created")
    private LocalDateTime timestamp;
}
