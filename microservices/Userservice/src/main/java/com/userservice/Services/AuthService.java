package com.userservice.Services;

import com.userservice.Dtos.*;
import com.userservice.Exceptions.EmailalreadyExistsException;
import com.userservice.Exceptions.UserNotFoundException;
import com.userservice.Models.Role;
import com.userservice.Models.Session;
import com.userservice.Models.Sessionstatus;
import com.userservice.Models.User;
import com.userservice.Repositories.UserRepo;
import com.userservice.Repositories.RoleRepo;
import com.userservice.Repositories.SessionRepo;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthService {

    private UserRepo userRepo;
    private SessionRepo sessionRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepo roleRepo;

    private SecretKey secretKey;


    @Autowired
    public AuthService(UserRepo userRepo, SessionRepo sessionRepo,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.sessionRepo = sessionRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepo = roleRepo;
        secretKey = Jwts.SIG.HS512.key().build();

    }

    public UserDto RegisterByemail(String email, String password, String name, String role) throws EmailalreadyExistsException {
        User user = new User();

        Optional<User> OpsUser = userRepo.findByemail(email);
        if(OpsUser.isPresent()) {
            throw new  EmailalreadyExistsException("email already exists");
        }
        Role role1 = new Role();
        role1.setRole(role);
        Role Savedrole = roleRepo.save(role1);

        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setName(name);
        user.getRoles().add(Savedrole);

        User savedUser = userRepo.save(user);

        UserDto userDto = UserDto.from(savedUser);
        return  userDto;

    }

    public ResponseEntity<UserDto> loginbByemail(String email, String password) throws UserNotFoundException {
        Optional<User> OPsUser = userRepo.findByemail(email);

        if(OPsUser.isEmpty()) {
            throw new UserNotFoundException("user with email :"+ email+ " not exits");
        }

        User Saveduser = OPsUser.get();

        if(!bCryptPasswordEncoder.matches(password, Saveduser.getPassword())) {
            throw new UserNotFoundException("password not matched");
        }

        Map<String, Object> jwtData = new HashMap<>();
        jwtData.put("email",email);
        jwtData.put("date", new Date());
        jwtData.put("expiry", new Date(LocalDate.now().plusDays(3).toEpochDay()));


        String jwsToken = Jwts.builder()
                .claims(jwtData)
                .signWith(secretKey)
                .compact();

        Session session = new Session();

//        String token = RandomStringUtils.randomAlphanumeric(24);
        // Set expiry date to 1 hour from now (in milliseconds)
//        long expiryMillis = now.getTime() + (60 * 60 * 1000); // 1 hour in milliseconds
        session.setToken(jwsToken);
        session.setSessionStatus(Sessionstatus.ACTIVE);
        session.setUser(Saveduser);

        sessionRepo.save(session);

        UserDto userDto = UserDto.from(Saveduser);

        MultiValueMap<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + jwsToken);

        return new ResponseEntity<UserDto>(userDto, headers, HttpStatus.OK);

    }

    public void logout(String token, Long id) {
        Optional<Session> Opssession = sessionRepo.findByTokenAndUser_Id(token, id);
        if(Opssession.isEmpty()) {
            return ;
        }
        Session Savedsession = Opssession.get();

        Savedsession.setSessionStatus(Sessionstatus.ENDED);
        sessionRepo.save(Savedsession);
    }


    public Sessionstatus Validate(String token, Long id) {
        Optional<Session> Opssession = sessionRepo.findByTokenAndUser_Id(token, id);
        if(Opssession.isEmpty()) {
            throw new RuntimeException();
        }

        Session Savedsession = Opssession.get();
        JwtData jwtData = new JwtData();
        if(!Savedsession.getSessionStatus().equals(Sessionstatus.ACTIVE)) {
            throw new RuntimeException();
        }

        Jws<Claims> jswclaims = Jwts.parser().
                verifyWith(secretKey).
                build().
                parseSignedClaims(token);

//       String email = claimsJwt.getPayload().get("email");
        return  Sessionstatus.ACTIVE;
    }
}
