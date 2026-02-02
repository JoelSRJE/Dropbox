package project.dropbox.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.dropbox.repositories.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
