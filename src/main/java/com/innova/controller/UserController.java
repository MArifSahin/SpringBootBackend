package com.innova.controller;

import com.innova.constants.ErrorCodes;
import com.innova.dto.request.*;
import com.innova.dto.response.SuccessResponse;
import com.innova.event.OnEditorApplicationSuccessEvent;
import com.innova.event.OnRegistrationSuccessEvent;
import com.innova.exception.BadRequestException;
import com.innova.exception.ErrorWhileSendingEmailException;
import com.innova.exception.UnauthorizedException;
import com.innova.model.ActiveSessions;
import com.innova.model.Role;
import com.innova.model.TokenBlacklist;
import com.innova.model.User;
import com.innova.security.jwt.JwtProvider;
import com.innova.security.services.UserDetailImpl;
import com.innova.service.ActiveSessionsService;
import com.innova.service.RoleService;
import com.innova.service.TokenBlacklistService;
import com.innova.service.UserService;
import com.innova.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    TokenBlacklistService tokenBlacklistService;

    @Autowired
    ActiveSessionsService activeSessionsService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/")
    public ResponseEntity<?> getUser() {
        UserDetailImpl userDetails = userService
                .getUserDetails(SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok().body(userDetails);
    }

    @PutMapping("/edit")
    @Transactional
    public ResponseEntity<?> editUser(@Valid @RequestBody ChangeForm changeForm) {
        User user = userService.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (changeForm.getEmail() != null && !changeForm.getEmail().equals(user.getEmail())) {
            if (userService.existsByEmail(changeForm.getEmail())) {
                return new ResponseEntity<String>("Email is already in use!", HttpStatus.BAD_REQUEST);
            }
            Set<ActiveSessions> activeSessionsForUserWithCurrentEmail = user.getActiveSessions();
            user.setActiveSessions(null);
            userService.saveUser(user);
            for (ActiveSessions activeSession : activeSessionsForUserWithCurrentEmail) {
                if (activeSessionsService.existsById(activeSession.getRefreshToken())) {
                    activeSessionsService.deleteSession(activeSession);
                }
            }
            userService.changeEmail(user, changeForm.getEmail());
            System.out.println("I cannot reach here!");
            try {
                eventPublisher.publishEvent(new OnRegistrationSuccessEvent(user, "/api/auth"));
            } catch (Exception re) {
                throw new ErrorWhileSendingEmailException(re.getMessage());
            }
        }
        userService.editUser(user, changeForm.getName(), changeForm.getLastname(), changeForm.getAge(),
                changeForm.getPhoneNumber());
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "User details successfuly changed.");
        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody LogoutForm logoutForm) throws IllegalArgumentException {
        if (logoutForm.getAccessToken() == null || logoutForm.getRefreshToken() == null) {
            throw new BadRequestException("Both tokens should be provided", ErrorCodes.REQUIRE_BOTH_TOKENS);
        } else {
            TokenBlacklist oldAccessToken = new TokenBlacklist(logoutForm.getAccessToken(), "access token");
            TokenBlacklist oldRefreshToken = new TokenBlacklist(logoutForm.getRefreshToken(), "refresh token");
            activeSessionsService.deleteSessionById(logoutForm.getRefreshToken());
            tokenBlacklistService.save(oldAccessToken);
            tokenBlacklistService.save(oldRefreshToken);
            SuccessResponse response = new SuccessResponse(HttpStatus.OK, "Successfully logged out");
            return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> createNewPassword(@RequestBody ChangePasswordForm changePasswordForm) {
        User user = userService.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (!changePasswordForm.checkAllFieldsAreGiven(changePasswordForm)) {
            throw new BadRequestException("All fields should be given", ErrorCodes.REQUIRE_ALL_FIELDS);
        } else {
            if (!passwordEncoder.matches(changePasswordForm.getOldPassword(), user.getPassword())) {
                throw new BadRequestException("Your old password is not correct",
                        ErrorCodes.OLD_PASSWORD_DOES_NOT_MATCH);
            } else if (!changePasswordForm.getNewPassword().equals(changePasswordForm.getNewPasswordConfirmation())) {
                throw new BadRequestException("Password fields does not match", ErrorCodes.NEW_PASSWORD_DOES_NOT_MATCH);
            } else if (changePasswordForm.getNewPassword().equals(changePasswordForm.getNewPasswordConfirmation())) {
                if (!PasswordUtil.isValidPassword(changePasswordForm.getNewPassword())) {
                    throw new BadRequestException("Password is not valid", ErrorCodes.PASSWORD_NOT_VALID);
                }
                userService.setNewPassword(user, changePasswordForm.getNewPassword());
                SuccessResponse response = new SuccessResponse(HttpStatus.OK, "Password successfully changed");
                return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
            } else {
                throw new BadRequestException("Something is wrong", ErrorCodes.SOMETHING_IS_WRONG);
            }
        }

    }

    @PostMapping("/create-new-password")
    public ResponseEntity<?> createNewPassword(@RequestBody ForgotAndChangePasswordForm forgotAndChangePasswordForm,
                                               HttpServletRequest request) {
        if (!forgotAndChangePasswordForm.checkAllFieldsAreGiven(forgotAndChangePasswordForm)) {
            throw new BadRequestException("All fields should be given", ErrorCodes.REQUIRE_ALL_FIELDS);
        } else {
            String token = forgotAndChangePasswordForm.getToken();
            if (token != null && jwtProvider.validateJwtToken(token, "password", request)) {
                User user = userService.getUserByToken(token, "password");
                if (!forgotAndChangePasswordForm.getNewPassword()
                        .equals(forgotAndChangePasswordForm.getNewPasswordConfirmation())) {
                    throw new BadRequestException("Password fields does not match",
                            ErrorCodes.NEW_PASSWORD_DOES_NOT_MATCH);
                } else if (forgotAndChangePasswordForm.getNewPassword()
                        .equals(forgotAndChangePasswordForm.getNewPasswordConfirmation())) {
                    userService.setNewPassword(user, forgotAndChangePasswordForm.getNewPassword());
                    SuccessResponse response = new SuccessResponse(HttpStatus.OK, "Password successfully changed");
                    return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
                }
            } else {
                throw new UnauthorizedException("Something is wrong with token", ErrorCodes.INVALID_ACCESS_TOKEN);
            }
        }
        throw new BadRequestException("Something is wrong", ErrorCodes.SOMETHING_IS_WRONG);
    }

    @GetMapping("/active-sessions")
    public ResponseEntity<?> getAllActiveSessions() {
        User user = userService.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Set<ActiveSessions> activeSessionsForUser = user.getActiveSessions();
        return ResponseEntity.ok().body(activeSessionsForUser);
    }

    @DeleteMapping("/logout-from-session")
    public ResponseEntity<?> logoutFromSession(@RequestParam("token") String refreshToken,
                                               @RequestParam("accessToken") String accessToken, HttpServletRequest request) {
        if (refreshToken != null) {
            if (jwtProvider.validateJwtToken(refreshToken, "refresh", request)) {
                activeSessionsService.deleteSessionById(refreshToken);
                TokenBlacklist oldRefreshToken = new TokenBlacklist(refreshToken, "refresh token");
                TokenBlacklist oldAccessToken = new TokenBlacklist(accessToken, "access token");
                tokenBlacklistService.save(oldRefreshToken);
                tokenBlacklistService.save(oldAccessToken);
                SuccessResponse response = new SuccessResponse(HttpStatus.OK,
                        "Successfully logged out from " + refreshToken);
                return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
            } else {
                throw new UnauthorizedException("All fields should be given", ErrorCodes.INVALID_REFRESH_TOKEN);
            }
        } else {
            throw new BadRequestException("Token must be given", ErrorCodes.REQUIRE_ALL_FIELDS);
        }
    }

    @PostMapping("/become-editor")
    public ResponseEntity<?> becomeEditor(@RequestBody EditorApplicationForm applicationForm) {
        try {
            Set<Role> roles = new HashSet<>();;
            Role role = roleService.findById(1).get();
            roles.add(role);
            User user = userService.findUserByRole(roles)
                    .orElseThrow(() -> new BadRequestException("User with given role could not found", ErrorCodes.NO_SUCH_USER));
            eventPublisher.publishEvent(new OnEditorApplicationSuccessEvent(user, applicationForm,"/api/auth"));
            SuccessResponse response = new SuccessResponse(HttpStatus.OK,"Form successfuly sent to admin.");
            return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
        }catch (Exception re) {
            throw new ErrorWhileSendingEmailException(re.getMessage());
        }
    }
}