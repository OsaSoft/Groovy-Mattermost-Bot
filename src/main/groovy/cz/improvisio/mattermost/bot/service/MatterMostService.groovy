package cz.improvisio.mattermost.bot.service

import groovy.util.logging.Slf4j
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

import static groovyx.net.http.ContentType.JSON

@Service
@Slf4j
class MatterMostService {
	static final ENDPOINTS = [
	        LOGIN: "users/login",
			LOGOUT: "users/logout",
			LIST_USERS: {offset, limit -> "users/$offset/$limit"},
			LIST_USERS_BY_ID: "users/id",
			SEARCH_USERS: "users/search",
			AUTOCOMPLETE_USER_TEAM: {team -> "teams/$team/users/autocomplete"},
			AUTOCOMPLETE_USER_CHANNEL: {team, channel -> "teams/$team/channels/$channel/users/autocomplete"},
			CREATE_USER: "users/create",
			UPDATE_USER: "users/update",
			UPDATE_USER_ROLES: "users/update_roles",
			UPDATE_USER_ACTIVE: "users/update_active",
			UPDATE_USER_NOTIFICATIONS: "users/update_notify",
			UPDATE_USER_PASSWORD: "users/newpassword",
			SEND_PASSWORD_RESET: "users/send_password_reset",
			PROFILE: "users/me",
			MY_TEAMS: "teams/all",
			MY_CHANNELS: {team -> "teams/$team/channels/"},
			MORE_CHANNELS: {team -> "teams/$team/channels/more"},
			GET_TEAM: {team -> "teams/$team/me"},
			CREATE_TEAM: "teams/create",
			UPDATE_TEAM: {team -> "teams/$team/update"},
			TEAM_USERS: {team, offset, limit -> "teams/$team/users/$offset/$limit"},
			TEAM_ADD_USER: {team -> "teams/$team/add_user_to_team"},
			TEAM_REMOVE_USER: {team -> "teams/$team/remove_user_from_team"},
			TEAM_MEMBERS: {team, offset, limit -> "teams/$team/members/$offset/$limit"},
			TEAM_MEMBER_BY_ID: {team, user -> "teams/$team/members/$user"},
			TEAM_MEMBERS_BY_ID: {team -> "teams/$team/members/ids"},
			TEAM_STATS: {team -> "teams/$team/stats"},
			GET_CHANNEL: {team, channel -> "teams/$team/channels/$channel"},
			CREATE_CHANNEL: {team -> "teams/$team/channels/create"},
			UPDATE_CHANNEL: {team -> "teams/$team/channels/update"},
			DELETE_CHANNEL: {team, channel -> "teams/$team/channels/$channel/delete"},
			CHANNEL_USERS: {team, channel, offset, limit -> "teams/$team/channels/$channel/users/$offset/$limit"},
			CHANNEL_ADD_USER: {team, channel -> "teams/$team/channels/$channel/add"},
			CHANNEL_USERS_NOT_IN_CHANNEL: {team, channel, offset, limit -> "teams/$team/channels/$channel/users/not_in_channel/$offset/$limit"},
			CHANNEL_MEMBERS: {team -> "teams/$team/channels/members"},
			CHANNEL_STATS: {team, channel -> "teams/$team/channels/$channel/stats"},
			CHANNEL_GET_MEMBER: {team, channel, user -> "teams/$team/channels/$channel/members/$user"},
			CHANNEL_MSGS: {team, channel, offset, limit -> "teams/$team/channels/$channel/posts/page/$offset/$limit"},
			CHANNEL_MSGS_SINCE: {team, channel, time -> "teams/$team/channels/$channel/posts/since/$time"},
			CHANNEL_MSGS_BEFORE_MSG: {team, channel, msg, offset, limit -> "teams/$team/channels/$channel/posts/$msg/before/$offset/$limit"},
			CHANNEL_MSGS_AFTER_MSG: {team, channel, msg, offset, limit -> "teams/$team/channels/$channel/posts/$msg/after/$offset/$limit"},
			MSG_GET: {team, channel -> "teams/$team/channels/$channel"},
			MSG_DELETE: {team, channel, msg -> "teams/$team/channels/$channel/posts/$msg/delete"},
			MSG_FIND: {team -> "teams/$team/posts/search"},
			MSG_GET_FLAGGED: {team, offset, limit -> "teams/$team/flagged/$offset/$limit"},
			MSG_SEND: {team, channel -> "teams/$team/channels/$channel/posts/create"},
			MSG_UPDATE: {team, channel -> "teams/$team/channels/$channel/posts/update"}
			//TODO: files, websockets, webhooks, preferences
	]

	@Value('${mattermost.url}')
	String url

	RESTClient mattermost

	@PostConstruct
	def init() {
		mattermost = new RESTClient(url, JSON)
	}

	String login(String username, String password) {
		//login and get token
		def resp = mattermost.post(
				path: ENDPOINTS.LOGIN,
				body: [login_id: username, password: password]
		)

		resp.headers.Token
	}

	boolean sendMessage(String token, String team, String channel, String message) {
		def resp = mattermost.post(
				path: ENDPOINTS.MSG_SEND(team, channel),
				headers: ["Authorization": "Bearer $token"],
				body: [channel_id: channel, message: message]
		)

		resp.status == 200
	}
}
