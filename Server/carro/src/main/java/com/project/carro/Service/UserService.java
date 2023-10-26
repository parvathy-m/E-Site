package com.project.carro.Service;

import com.project.carro.Dto.Form.LoginForm;
import com.project.carro.Dto.Form.UserDetailsUpdateForm;
import com.project.carro.Dto.View.UserInfo;
import com.project.carro.Dto.View.UserLoginView;
import com.project.carro.Dto.View.UserView;
import com.project.carro.Entity.UserEntity;
import com.project.carro.Exceptions.BadRequestException;
import com.project.carro.Dto.Form.UserRegisterForm;
import com.project.carro.Exceptions.NotFoundException;
import com.project.carro.Repository.UserRepository;
import com.project.carro.Utils.JWT.AccessTokenGenerator;
import com.project.carro.Utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccessTokenGenerator accessTokenGenerator;
    @Autowired
    CommonUtils commonUtils;

    public void userRegister(UserRegisterForm form) {
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        form.setPassword(encodedPassword);
        UserEntity user = userRepository.findByUserNameOrEmailAndRole(form.getUserName(), form.getEmail(),UserEntity.Role.USER.getValue());
        if (user != null) {
            log.info("UserName [ {} ] or Email [ {} ] already registered", user.getUserName(), user.getEmail());
            throw new BadRequestException("invalid.user");
        }
        UserEntity newUser = new UserEntity();
        newUser.setStatus(UserEntity.Status.ACTIVE.getValue());
        newUser.setRole(UserEntity.Role.USER.getValue());
        newUser.setPassword(form.getPassword());
        if (form.getUserName() == null) {
            String[] name = form.getEmail().split("@", 2);
            newUser.setUserName(name[0]);
        } else newUser.setUserName(form.getUserName());
        newUser.setEmail(form.getEmail());
        newUser.setCreatedDate(new Date());
        newUser.setUpdatedDate(new Date());
        userRepository.save(newUser);
        log.info("User saved");
    }

    public UserLoginView login(LoginForm form, int value) {
        log.info("login request received");
        UserLoginView loginView = null;
        UserEntity user = null;
        user = userRepository.findByEmailAndRole(form.getUserName(),value);
        if (user == null) {
            log.info("user not found");
            throw new NotFoundException("user.not.found");
        }
        if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            if (user.getStatus() != UserEntity.Status.ACTIVE.getValue()) {
                log.info("user not Active");
                throw new NotFoundException("user.not.found");
            }
            String accessToken = accessTokenGenerator.generateAccessToken(user);
            String encodedToken = new String(Base64.encodeBase64(accessToken.getBytes()));
            loginView = UserLoginView.builder()
                    .userId(String.valueOf(user.getUserId()))
                    .accessToken(encodedToken)
                    .userName(user.getUserName())
                    .email(user.getEmail())
                    .build();
            return loginView;
        } else {
            log.info("Invalid Password");
            throw new BadRequestException("invalid.cred");
        }
    }

    public UserInfo userInfo() {
        String userName = commonUtils.getUserName();
        UserEntity user = userRepository.findByEmail(userName);
        return new UserInfo(user);
    }

    public void updateUser(UserDetailsUpdateForm form) {
        UserEntity user = userRepository.findByEmail(commonUtils.getUserName());

        if (form.getFirstName() != null) {
            user.setFName(form.getFirstName());
        } else user.setFName(user.getFName());
        if (form.getLastName() != null) {
            user.setLName(form.getLastName());
        } else user.setLName(user.getLName());
        if (form.getGender() != null) {
            user.setGender(form.getGender());
        } else user.setGender(user.getGender());
        if (form.getMobile() != null) {
            user.setMobile(form.getMobile());
        } else user.setMobile(user.getMobile());
        userRepository.save(user);
        log.info("user info updated");
    }

    public List<Object[]> updateDemo() {
        List<Object[]> user = userRepository.getUser();
        return user;
    }
}
