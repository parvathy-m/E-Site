package com.project.carro.Utils;

import com.project.carro.Entity.UserEntity;
import com.project.carro.Repository.UserRepository;
import com.project.carro.Utils.JWT.AccessTokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    @Autowired
    private AccessTokenGenerator accessTokenGenerator;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserRepository userRepository;

    public String getUserName(){
        String token=new String(Base64.decodeBase64(request.getHeader("Authorization")));
        return accessTokenGenerator.getUsernameFromToken(token);
    }

    public Long getCurrentUserId(){
        String token=new String(Base64.decodeBase64(request.getHeader("Authorization")));
        String email= accessTokenGenerator.getUsernameFromToken(token);
        UserEntity user=userRepository.findByEmail(email);
        return user.getUserId();
    }

    public String flag(int flag) {
        return switch (flag) {
            case 0 -> CommonConstants.Flag.DELETED.getType();
            case 1 -> CommonConstants.Flag.ACTIVE.getType();
            case 2 -> CommonConstants.Flag.BLOCKED.getType();
            default -> throw new IllegalStateException("Unexpected value: " + flag);
        };
    }

    public String status(int status) {
        return switch (status) {
            case 0 -> CommonConstants.Status.REJECTED.getType();
            case 1 -> CommonConstants.Status.APPROVED.getType();
            case 2 -> CommonConstants.Status.PENDING.getType();
            case 3 -> CommonConstants.Status.ON_HOLD.getType();
            default -> throw new IllegalStateException("Unexpected value: " + status);
        };
    }

}
