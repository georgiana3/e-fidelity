package ro.efidelity.util;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {

	/**
	 * Get info about currently logged in user
	 * 
	 * @return UserDetails if found in the context, null otherwise
	 */
	public static final Authentication getAuthentication() {
		Authentication authentication = (Authentication) SecurityContextHolder
				.getContext().getAuthentication();
		return authentication;
	}

	public static final boolean hasRole(String... roles) {
		boolean hasRole = false;
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			@SuppressWarnings("unchecked")
			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication
					.getAuthorities();
			for (String role : roles)
				if (isRolePresent(authorities, role)) {
					hasRole = true;
				}
		}
		return hasRole;
	}

	/**
	 * Check if a role is present in the authorities of current user
	 * 
	 * @param authorities
	 *            all authorities assigned to current user
	 * @param role
	 *            required authority
	 * @return true if role is present in list of authorities assigned to
	 *         current user, false otherwise
	 */
	private static final boolean isRolePresent(
			Collection<GrantedAuthority> authorities, String role) {
		boolean isRolePresent = false;
		for (GrantedAuthority grantedAuthority : authorities) {
			isRolePresent = grantedAuthority.getAuthority().equals(role);
			if (isRolePresent)
				break;
		}
		return isRolePresent;
	}

}
