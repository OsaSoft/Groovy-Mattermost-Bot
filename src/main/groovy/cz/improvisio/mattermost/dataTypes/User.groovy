package cz.improvisio.mattermost.dataTypes

import groovy.transform.ToString
import groovy.util.logging.Slf4j

/**
 * Params as of Mattermost v. 3.6.0
 * @author OsaSoft https://github.com/OsaSoft
 */
@ToString
@Slf4j
class User extends MattermostDatatype {
	String id
	long create_at
	long update_at
	long delete_at
	String username
	String first_name
	String last_name
	String nickname
	String email
	boolean email_verified
	String password
	String auth_data
	String auth_service
	String position
	String roles
	String locale
	boolean allow_marketing
	def notify_props //TODO
	def props //TODO
	long last_password_update
	long last_picture_update
	long failed_attempts
	boolean mfa_active
	String mfa_secret
}
