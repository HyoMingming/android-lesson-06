package kr.easw.lesson06.service;

import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import kr.easw.lesson06.model.dto.UserAuthenticationDto;
import kr.easw.lesson06.model.dto.UserDataEntity;
import kr.easw.lesson06.model.repository.UserDataRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {

    private final UserDataRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    @PostConstruct
    public void init() {
        System.out.println("Creating admin and guest users");
        createUser(new UserDataEntity(0L, "admin", encoder.encode("admin"), true));
        createUser(new UserDataEntity(0L, "guest", encoder.encode("guest"), false));
    }

    public boolean isUserExists(String userId) {
        return repository.findUserDataEntityByUserId(userId).isPresent();
    }

    public void createUser(UserDataEntity entity) {
        repository.save(entity);
    }

    public List<UserDataEntity> getAllUsers() {
        return repository.findAll();
    }

    public boolean removeUser(String userId) {
        Optional<UserDataEntity> userOpt = repository.findByUserId(userId);
        return userOpt.map(user -> {
            repository.delete(user);
            return true;
        }).orElse(false);
    }

    @Nullable
    public UserAuthenticationDto createTokenWith(UserDataEntity userDataEntity) {
        Optional<UserDataEntity> entityOpt = repository.findUserDataEntityByUserId(userDataEntity.getUserId());
        return entityOpt.map(archivedEntity -> {
            if (encoder.matches(userDataEntity.getPassword(), archivedEntity.getPassword())) {
                return new UserAuthenticationDto(jwtService.generateToken(archivedEntity.getUserId()));
            } else {
                throw new BadCredentialsException("Credentials invalid");
            }
        }).orElseThrow(() -> new BadCredentialsException("Credentials invalid"));
    }

    public UserDataService(final UserDataRepository repository, final BCryptPasswordEncoder encoder, final JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }
}
