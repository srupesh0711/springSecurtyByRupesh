package com.SSR.Service;

import com.SSR.Entity.App;
import com.SSR.Payloads.AppDto;
import com.SSR.Repository.AppRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppService {
    private AppRepository appRepository;
    private JWTService jwtService;

    public AppService(AppRepository appRepository, JWTService jwtService) {
        this.appRepository = appRepository;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> createUser(App app) {
        Optional<App> opUsername = appRepository.findByUsername(app.getUsername());
        if (opUsername.isPresent()){
            return new ResponseEntity<>("Username already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<App> opEmail = appRepository.findByEmail(app.getEmail());
        if (opEmail.isPresent()){
            return new ResponseEntity<>("Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String encrypted = BCrypt.hashpw(app.getPassword(), BCrypt.gensalt(5));
        app.setPassword(encrypted);
        App saved = appRepository.save(app);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    public String createLoginUser(AppDto appDto) {
        Optional<App> optUsername = appRepository.findByUsername(appDto.getUsername());
        if (optUsername.isPresent()){
            App app = optUsername.get();
            if (BCrypt.checkpw(appDto.getPassword(),app.getPassword())) {
                String token = jwtService.createToken(appDto.getUsername());
                return token;
            }
        }else {
            return null;
        }
        return null;
    }
}
