package ru.vsu.hb.controller;

import com.auth0.jwt.JWT;
import com.leakyabstractions.result.Results;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.DefaultCategory;
import ru.vsu.hb.dto.UserDto;
import ru.vsu.hb.dto.UserLoginRequest;
import ru.vsu.hb.dto.error.EntityNotFoundError;
import ru.vsu.hb.dto.error.HBError;
import ru.vsu.hb.dto.response.HBResponseData;
import ru.vsu.hb.persistence.entity.User;
import ru.vsu.hb.service.CategoryService;
import ru.vsu.hb.service.UserService;
import ru.vsu.hb.utils.HBResponseBuilder;

import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static ru.vsu.hb.security.SecurityConstants.*;

@RestController
@RequestMapping(value = "/user", produces = {"application/json"})
@Api(description = "Пользователь")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    @ApiOperation(value = "Авторизация пользователя", response = HBResponseData.class)
    public ResponseEntity<? extends HBResponseData<? extends UserDto>> login(@RequestBody UserLoginRequest user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("USER"))));
        String token = JWT.create().withSubject(((UserDetails) authentication.getPrincipal()).getUsername())
                .sign(HMAC512(SECRET.getBytes()));
        var result = authentication.isAuthenticated()
                ? userService.getUserDtoByEmail(authentication.getName())
                .mapSuccess(u -> {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return u;
                }) : Results.failure(new HBError("forbidden", "Auth exception")).mapSuccess(it -> (UserDto) it);
        var status = result.isSuccess() ? HttpStatus.OK : HttpStatus.FORBIDDEN;
        return HBResponseBuilder.fromHBResult(result).withStatus(status).withAuthToken(TOKEN_PREFIX + token).build();
    }

    @PostMapping("/register")
    @ApiOperation(value = "Регистрация пользователя")
    public ResponseEntity<? extends HBResponseData<? extends UserDto>> register(@RequestBody User user) {
        var result = userService.getUserDtoByEmail(user.getUserEmail())
                .flatMapSuccess(u -> Results.failure(new HBError("client_error", "User with email " + user.getUserEmail() + " already exists"))
                        .mapSuccess(it -> (UserDto) it))
                .flatMapFailure(error -> {
                    if (error instanceof EntityNotFoundError) {
                        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

                        return Results.success(
                                UserDto.fromEntity(userService.createUser(user)))
                                .mapSuccess(it->{
                                    for(CategoryDto categoryDto: DefaultCategory.getDefaultCategories()){
                                        categoryService.addCategory(categoryDto, user.getUserEmail());
                                    }
                                    return it;
                                })
                                .mapFailure(it -> (HBError) it);
                    } else {
                        return Results.failure(error).mapSuccess(it -> (UserDto) it);
                    }
                });
        var status = result.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT;
        /*if(result.isSuccess()){
            for(CategoryDto categoryDto: DefaultCategory.getDefaultCategories()){
                categoryService.addCategory(categoryDto, user.getUserEmail());
            }
        }*/
        return HBResponseBuilder.fromHBResult(result).withStatus(status).build();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping
    @ApiOperation(value = "Редактирование пользователя")
    public ResponseEntity<? extends HBResponseData<? extends UserDto>> editUser(@RequestBody User user) {
        return HBResponseBuilder.fromHBResult(userService.editUser(user)).build();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/{userEmail}")
    @ApiOperation(value = "Получение пользователя по email")
    public ResponseEntity<? extends HBResponseData<? extends UserDto>> getUserByEmail(@PathVariable @ApiParam(value = "Email пользователя") String userEmail) {
        return HBResponseBuilder.fromHBResult(userService.getUserDtoByEmail(userEmail)).build();
    }
}
