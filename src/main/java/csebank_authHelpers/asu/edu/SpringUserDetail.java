package csebank_authHelpers.asu.edu;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import csebank_controllers.asu.edu.ControllerUtility;
import csebank_objectmodel.asu.edu.User;
import csebank_utility.asu.edu.DbParamNams;

public class SpringUserDetail implements UserDetails{
	User user;
	public SpringUserDetail(User user) {
		//TODO uncomment below line
		//ControllerUtility.validateUser(user);
		this.user=user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//set the user role here
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	    authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getUserRole().toUpperCase()));
	    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	    return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {		
		return user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		int attempt=user.getLoginAttemptNumber()!=null?Integer.parseInt(user.getLoginAttemptNumber()):0;
		return attempt<ControllerUtility.LOGIN_ATTEMPT_MAX_NUM && attempt>-1;
	}

	@Override
	public boolean isCredentialsNonExpired() {		
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getUserStatus().equalsIgnoreCase(DbParamNams.USER_STATUS_ACTIVE);
	}

}
