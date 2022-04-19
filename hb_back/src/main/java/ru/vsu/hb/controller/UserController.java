package ru.vsu.hb.controller;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.vsu.hb.dto.UserDto;
import ru.vsu.hb.dto.response.HBResponseData;
import ru.vsu.hb.persistence.entity.User;
import ru.vsu.hb.service.UserService;

import java.util.UUID;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static ru.vsu.hb.security.SecurityConstants.*;
import static ru.vsu.hb.utils.ControllerUtils.toHBResult;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getAuthorities()));
        if (authentication.isAuthenticated()) {
            String token = JWT.create().withSubject(((UserDetails) authentication.getPrincipal()).getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(HMAC512(SECRET.getBytes()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User userInDb = userService.findUserByEmail(authentication.getName());

            return ResponseEntity.ok().header(HEADER_STRING, TOKEN_PREFIX + token).body(userInDb);
        } else return ResponseEntity.status(403).build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User userInDb = userService.findUserByEmail(user.getEmail());
        if (userInDb != null)
            return ResponseEntity.status(409).build();
        else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            User newUser = userService.createUser(user);
            return ResponseEntity.ok(newUser);
        }
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping
    public ResponseEntity<? super HBResponseData<? super UserDto>> editUser(@RequestBody User user) {
        return toHBResult(userService.editUser(user));
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<? super HBResponseData<? super UserDto>> getUserById(@PathVariable UUID userId) {
        return toHBResult(userService.getDtoById(userId));
    }
}
