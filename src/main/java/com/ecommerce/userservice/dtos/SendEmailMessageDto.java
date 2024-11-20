package com.ecommerce.userservice.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SendEmailMessageDto {
    private String to;
    private String subject;
    private String body;
}
