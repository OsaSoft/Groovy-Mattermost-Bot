package cz.improvisio.mattermost.bot.bots

import cz.improvisio.mattermost.bot.service.MatterMostService
import org.springframework.beans.factory.annotation.Autowired

abstract class ABot implements Runnable {
	MatterMostService matterMostService

	String username
	String password
	String token

	ABot(MatterMostService matterMostService) {
		this.matterMostService = matterMostService
	}

	ABot init() {
		token = matterMostService.login(username, password)
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
