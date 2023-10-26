package com.project.carro.Dto.View;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginView {
    private String userId;
    private String userName;
    private String email;
    private String accessToken;
}
