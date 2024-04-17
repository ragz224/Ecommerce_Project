package com.userservice.Controllers;

import com.userservice.Dtos.*;
import com.userservice.Exceptions.EmailalreadyExistsException;
import com.userservice.Exceptions.UserNotFoundException;
import com.userservice.Models.Sessionstatus;
import com.userservice.Models.User;
import com.userservice.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Auth")
public class AuthController {
    AuthService authService;

    @Autowired
    public AuthController(AuthService userService) {
        this.authService = userService;
    }


    @PostMapping("/Signup")
    public ResponseEntity<UserDto> Signup(@RequestBody SignupRequestDto signupRequestDto) throws EmailalreadyExistsException {
        System.out.println("signup");
        UserDto userDto = authService.RegisterByemail(signupRequestDto.getEmail(), signupRequestDto.getPassword(),signupRequestDto.getName(), signupRequestDto.getRole());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFoundException {
        return authService.loginbByemail(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }


    @PostMapping("/validate")
    public ResponseEntity<Sessionstatus> validate(@RequestBody ValidTokenDto validTokenDto) {
        Sessionstatus sessionstatus = authService.Validate(validTokenDto.getToken(), validTokenDto.getId());
        return new ResponseEntity<>(sessionstatus,HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
       authService.logout(logoutRequestDto.getToken(),logoutRequestDto.getUserId());
       return ResponseEntity.ok().build();
    }
}
