package ch.bbzbl.time.security;

import ch.bbzbl.time.data.entity.User;
import ch.bbzbl.time.data.service.UserRepository;
import com.vaadin.flow.spring.security.AuthenticationContext;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Helper class for retrieving the authenticated user and performing logout.
 */
@Component
public class AuthenticatedUser {

    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;

    /**
     * Constructs an instance of AuthenticatedUser.
     *
     * @param authenticationContext The authentication context
     * @param userRepository       The user repository
     */
    public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }

    /**
     * Retrieves the authenticated user.
     *
     * @return The optional authenticated user
     */
    public Optional<User> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> userRepository.findByUsername(userDetails.getUsername()));
    }

    /**
     * Performs the logout operation.
     */
    public void logout() {
        authenticationContext.logout();
    }
}
