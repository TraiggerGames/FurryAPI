package es.recha.furry.service;

import es.recha.furry.model.User;
import es.recha.furry.repositories.UserRepository;
import es.recha.furry.service.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no existe: " + id));
    }
}
