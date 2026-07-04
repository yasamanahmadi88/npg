package ix.portal.npg.security;

import ix.portal.npg.domain.User;
import ix.portal.npg.repository.UserRepository;
import ix.portal.npg.service.ResourceAuthorityQueryService;
import ix.portal.npg.service.dto.ResourceAuthorityDTO;
import java.util.*;
import java.util.stream.Collectors;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);
    private final ResourceAuthorityQueryService resourceAuthorityQueryService;
    private final UserRepository userRepository;

    public DomainUserDetailsService(ResourceAuthorityQueryService resourceAuthorityQueryService, UserRepository userRepository) {
        this.resourceAuthorityQueryService = resourceAuthorityQueryService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        if (new EmailValidator().isValid(login, null)) {
            return userRepository
                .findOneWithAuthoritiesByEmailIgnoreCase(login)
                .map(user -> createSpringSecurityUser(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository
            .findOneWithAuthoritiesByLogin(lowercaseLogin)
            .map(user -> createSpringSecurityUser(lowercaseLogin, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }

    private UserDetails createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user
            .getAuthorities()
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());

        List<String> ids = new ArrayList<>();
        grantedAuthorities.forEach(
            grantedAuthority -> {
                ids.add(grantedAuthority.getAuthority());
            }
        );

        List<ResourceAuthorityDTO> resources = resourceAuthorityQueryService.findByAuthorities(ids, Pageable.unpaged()).getContent();

        return new PortalUser(
            user.getLogin(),
            user.getPassword(),
            user.isActivated(),
            true,
            true,
            true,
            grantedAuthorities,
            user.getPartyId() + "",
            resources,
            user
        );
    }
}


