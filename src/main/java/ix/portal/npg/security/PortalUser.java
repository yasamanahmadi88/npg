package ix.portal.npg.security;

//import ir.bp.iaari.domain.User;

import ix.portal.npg.domain.User;
import ix.portal.npg.service.dto.ResourceAuthorityDTO;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Hossein Sadeghi on 9/4/2019.
 */
public class PortalUser extends org.springframework.security.core.userdetails.User {

    private List<ResourceAuthorityDTO> resourceAuthorities;
    private String partyId;
    private User user;

    public PortalUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public PortalUser(
        String username,
        String password,
        boolean enabled,
        boolean accountNonExpired,
        boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public PortalUser(
        String username,
        String password,
        boolean enabled,
        boolean accountNonExpired,
        boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities,
        String partyId,
        List<ResourceAuthorityDTO> resourceAuthorities,
        User user
    ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.resourceAuthorities = resourceAuthorities;
        this.partyId = partyId;
        this.user = user;
    }

    public List<ResourceAuthorityDTO> getResourceAuthorities() {
        return resourceAuthorities;
    }

    public void setResourceAuthorities(List<ResourceAuthorityDTO> resourceAuthorities) {
        this.resourceAuthorities = resourceAuthorities;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


