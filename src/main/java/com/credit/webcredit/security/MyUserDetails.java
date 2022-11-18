package com.credit.webcredit.security;

import com.credit.webcredit.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetails implements UserDetails {

	private final Users users;
	
	public MyUserDetails(Users users) {
		this.users = users;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(users.getRole().name());
		return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() {
		return users.getPassword();
	}

	@Override
	public String getUsername() {
		return users.getPhone();
	}

	public Long getId(){
		return users.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
