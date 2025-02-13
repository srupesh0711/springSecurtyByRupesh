package com.SSR.Controller;

import com.SSR.Entity.App;
import com.SSR.Payloads.AppDto;
import com.SSR.Payloads.TokenDto;
import com.SSR.Service.AppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/a1/appController")
// http://localhost:8080/api/a1/appController
public class AppController {

    private AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping("/up")
    public ResponseEntity<?> createSignUp(
           @RequestBody App app
    ){
        ResponseEntity<?> user = appService.createUser(app);
        return user;

    }

    @PostMapping("/in")
    public ResponseEntity<?> createLogin(
            @RequestBody AppDto appDto
    ){
        String loginUser = appService.createLoginUser(appDto);
        if (loginUser!=null) {
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(loginUser);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN);
        }
    }
}
