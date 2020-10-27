package com.innova.controller;


import com.innova.message.request.ChangePasswordForm;
import com.innova.message.request.ForgotAndChangePasswordForm;
import com.innova.message.request.LogoutForm;
import com.innova.exception.BadRequestException;
import com.innova.message.request.ChangeForm;
import com.innova.model.ActiveSessions;
import com.innova.model.TokenBlacklist;
import com.innova.model.User;
import com.innova.repository.ActiveSessionsRepository;
import com.innova.repository.TokenBlacklistRepository;
import com.innova.repository.UserRepository;
import com.innova.security.jwt.JwtProvider;
import com.innova.security.services.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    ActiveSessionsRepository activeSessionsRepository;

    @Autowired
    JwtProvider jwtProvider;

    @GetMapping("/")
    public ResponseEntity<?> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
            return ResponseEntity.ok().body(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Arrays.asList("Please sign in for retrieving user information."));
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editUser(@Valid @RequestBody ChangeForm changeForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();

            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User cannot find"));
            if (changeForm.getEmail() != null || changeForm.getAge() != null || changeForm.getName() != null
                    || changeForm.getLastname() != null || changeForm.getPhoneNumber() != null) {
                if (changeForm.getEmail() != null) {
                    if (userRepository.existsByEmail(changeForm.getEmail())) {
                        return new ResponseEntity<String>("Email is already in use!", HttpStatus.BAD_REQUEST);
                    }
                    user.setEmail(changeForm.getEmail());
                }
                if (changeForm.getName() != null)
                    user.setName(changeForm.getName());
                if (changeForm.getLastname() != null)
                    user.setLastname(changeForm.getLastname());
                if (changeForm.getAge() != null)
                    user.setAge(changeForm.getAge());
                if (changeForm.getPhoneNumber() != null)
                    user.setPhoneNumber(changeForm.getPhoneNumber());

                userRepository.save(user);

                return ResponseEntity.ok(Arrays.asList(user.toString()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Arrays.asList("You are not changing anything."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Arrays.asList("Please sign in for retrieving user information."));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody LogoutForm logoutForm) throws IllegalArgumentException {
        Map<String, Object> myMap = new HashMap<>();
        myMap.put("timestamp", new Date());
        myMap.put("path", "api/auth/logout");
        if (logoutForm.getAccessToken() == null || logoutForm.getRefreshToken() == null) {
            myMap.put("error", "Both tokens should be provided");
            myMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(myMap, HttpStatus.BAD_REQUEST);
        } else {
            TokenBlacklist oldAccessToken = new TokenBlacklist(logoutForm.getAccessToken(), "access token");
            TokenBlacklist oldRefreshToken = new TokenBlacklist(logoutForm.getRefreshToken(), "refresh token");
            activeSessionsRepository.deleteById(logoutForm.getRefreshToken());
            tokenBlacklistRepository.save(oldAccessToken);
            tokenBlacklistRepository.save(oldRefreshToken);
            myMap.put("message", "Successfully logged out");
            myMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(myMap, HttpStatus.OK);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> createNewPassword(@RequestBody ChangePasswordForm changePasswordForm){
        Map<String, Object> myMap = new HashMap<>();
        myMap.put("timestamp", new Date());
        myMap.put("path", "api/auth/forgot-password");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User cannot find"));
        

            if (!changePasswordForm.checkAllFieldsAreGiven(changePasswordForm)) {
                myMap.put("error", "All fields should be given");
                myMap.put("status", HttpStatus.BAD_REQUEST.value());
                return new ResponseEntity<>(myMap, HttpStatus.BAD_REQUEST);
            } else {
                if(!passwordEncoder.matches(changePasswordForm.getOldPassword(), user.getPassword())){
                    myMap.put("error", "Your old password is not correct");
                    myMap.put("status", HttpStatus.BAD_REQUEST.value());
                    return new ResponseEntity<>(myMap, HttpStatus.BAD_REQUEST);
                }
                else if(!changePasswordForm.getNewPassword().equals(changePasswordForm.getNewPasswordConfirmation())){
                    myMap.put("error", "Password fields does not match");
                    myMap.put("status", HttpStatus.BAD_REQUEST.value());
                    return new ResponseEntity<>(myMap, HttpStatus.BAD_REQUEST);
                }
                else if(changePasswordForm.getNewPassword().equals(changePasswordForm.getNewPasswordConfirmation())){
                    user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
                    userRepository.save(user);
                    myMap.put("message", "Password successfully changed");
                    myMap.put("status", HttpStatus.OK.value());
                    return new ResponseEntity<>(myMap, HttpStatus.OK);
                }
                else{
                    myMap.put("error", "Something is wrong");
                    myMap.put("status", HttpStatus.BAD_REQUEST.value());
                    return new ResponseEntity<>(myMap, HttpStatus.BAD_REQUEST);
                }
            }
        }
        else{
            myMap.put("error", "Unauthorized access");
            myMap.put("status", HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(myMap, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/create-new-password")
    public ResponseEntity<?> createNewPassword(@RequestBody ForgotAndChangePasswordForm forgotAndChangePasswordForm, HttpServletRequest request){
        Map<String, Object> myMap = new HashMap<>();
        myMap.put("timestamp", new Date());
        myMap.put("path", "api/auth/create-new-password");
        if(!forgotAndChangePasswordForm.checkAllFieldsAreGiven(forgotAndChangePasswordForm)){
            myMap.put("error", "All fields should be given");
            myMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity(myMap, HttpStatus.BAD_REQUEST);
        }
        else{
            String token = forgotAndChangePasswordForm.getToken();
            if(token!= null && jwtProvider.validateJwtToken(token,"password",request)){
                String email = jwtProvider.getSubjectFromJwt(token, "password");
                User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Fail! -> Cause: User not found."));
                if(!forgotAndChangePasswordForm.getNewPassword().equals(forgotAndChangePasswordForm.getNewPasswordConfirmation())){
                    myMap.put("error", "Password fields does not match");
                    myMap.put("status", HttpStatus.BAD_REQUEST.value());
                    return new ResponseEntity(myMap, HttpStatus.BAD_REQUEST);
                }
                else if(forgotAndChangePasswordForm.getNewPassword().equals(forgotAndChangePasswordForm.getNewPasswordConfirmation())){
                    user.setPassword(passwordEncoder.encode(forgotAndChangePasswordForm.getNewPassword()));
                    userRepository.save(user);
                    myMap.put("message", "Password successfully changed");
                    myMap.put("status", HttpStatus.OK.value());
                    return new ResponseEntity(myMap, HttpStatus.OK);
                }
                else{
                    myMap.put("error", "Something is wrong");
                    myMap.put("status", HttpStatus.BAD_REQUEST.value());
                    return new ResponseEntity(myMap, HttpStatus.BAD_REQUEST);
                }
            }
            else{
                myMap.put("error", "Something is wrong with that token!");
                myMap.put("status", HttpStatus.BAD_REQUEST.value());
                return new ResponseEntity(myMap, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/active-sessions")
    public ResponseEntity<?> getAllActiveSessions(){
        Map<String, Object> myMap = new HashMap<>();
        myMap.put("timestamp", new Date());
        myMap.put("path", "api/auth/active-sessions");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
            User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User cannot find"));

            Set<ActiveSessions> activeSessionsForUser = user.getActiveSessions();
            return ResponseEntity.ok().body(activeSessionsForUser);
        }
        else{
            myMap.put("error", "Something is wrong with authentication");
            myMap.put("status", HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(myMap, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/logout-from-session")
    public ResponseEntity<?> logoutFromSession(@RequestParam("token")String refreshToken, HttpServletRequest request){
        Map<String, Object> myMap = new HashMap<>();
        myMap.put("timestamp", new Date());
        myMap.put("path", "api/auth/active-sessions");
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            if(refreshToken != null){
                if(jwtProvider.validateJwtToken(refreshToken, "refresh", request)){
                    activeSessionsRepository.deleteById(refreshToken);
                    myMap.put("message", "Successfully logged out from " + refreshToken);
                    myMap.put("status", HttpStatus.OK.value());
                    TokenBlacklist oldRefreshToken = new TokenBlacklist(refreshToken, "refresh token");
                    tokenBlacklistRepository.save(oldRefreshToken);
                    return ResponseEntity.ok().body(myMap);
                }
                else{
                    myMap.put("error", "Something is wrong with given token!");
                    myMap.put("status", HttpStatus.BAD_REQUEST.value());
                    return new ResponseEntity<>(myMap, HttpStatus.BAD_REQUEST);
                }
            }
            else{
                myMap.put("error", "Token must be given!");
                myMap.put("status", HttpStatus.BAD_REQUEST.value());
                return new ResponseEntity<>(myMap, HttpStatus.BAD_REQUEST);
            }
        }
        else{
            myMap.put("error", "Something is wrong with authentication");
            myMap.put("status", HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(myMap, HttpStatus.UNAUTHORIZED);
        }
    }
}