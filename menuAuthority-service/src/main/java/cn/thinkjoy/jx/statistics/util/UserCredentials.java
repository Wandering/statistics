/*
 * @author: ${author}
 * @since: 1.0.0
 * @version: 1.0.0
 */
package cn.thinkjoy.jx.statistics.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * The Class UserCredentials.
 */
public class UserCredentials extends User {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1524661454123145417L;

	/** The Constant CACHE_NAME. */
	public static final String CACHE_NAME = "users:credentials";

	/**
	 * Instantiates a new user credentials.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param enabled
	 *            the enabled
	 * @param accountNonExpired
	 *            the account non expired
	 * @param credentialsNonExpired
	 *            the credentials non expired
	 * @param accountNonLocked
	 *            the account non locked
	 * @param authorities
	 *            the authorities
	 */
	public UserCredentials(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	/**
	 * 获取用户账户ID，
	 *
	 * @return the uid
	 */
	public Integer getUid() {
		return Integer.valueOf(this.getUsername().split(",")[0]);
	}

	/**
	 * 获取用户账户ID
	 *
	 * @return
	 */
	public String getUids() {
		return this.getUsername().split(",")[0];
	}

	/**
	 * 用户类型：1老师，2家长，3学生
	 *
	 * @return
	 */
	public Integer getUserType() {
		return Integer.valueOf(this.getUsername().split(",")[1]);
	}

	/**
	 * 用户类型：1老师，2家长，3学生
	 *
	 * @return
	 */
	public String getUserTypes() {
		return this.getUsername().split(",")[1];
	}

	/**
	 * Checks for role.
	 *
	 * @param roleName
	 *            the role name
	 * @return true, if successful
	 */
	public boolean hasRole(String roleName) {
		return this.getAuthorities().contains(new SimpleGrantedAuthority(roleName));
	}
}
