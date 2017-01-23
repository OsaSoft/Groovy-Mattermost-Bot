package cz.improvisio.mattermost.bot.bots

import cz.improvisio.mattermost.bot.service.MatterMostService
import cz.improvisio.mattermost.dataTypes.User
import org.springframework.beans.factory.annotation.Autowired

abstract class ABot implements Runnable {
	@Autowired
	MatterMostService matterMostService

	String username
	String password
	String token

	User user

	ABot(MatterMostService matterMostService) {
		this.matterMostService = matterMostService
	}

	ABot init() {
		def result = matterMostService.login(username, password)
		if (!result) {
			return null
		}

		token = result.token
		user = result.user
		this
	}

	String getUsername() {
		return username
	}

	void setUsername(String username) {
		this.username = username
	}

	String getPassword() {
		return password
	}

	void setPassword(String password) {
		this.password = password
	}
}
