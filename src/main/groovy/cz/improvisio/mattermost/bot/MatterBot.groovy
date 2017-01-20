package cz.improvisio.mattermost.bot

import cz.improvisio.mattermost.bot.bots.ABot
import cz.improvisio.mattermost.bot.service.MatterMostService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@Slf4j
class MatterBot implements CommandLineRunner{
	@Autowired
	MatterMostService matterMostService

	@Value('${mattermost.loginId}')
	String username

	@Value('${mattermost.password}')
	String password

	@Override
	void run(String... args) {

	}

	static void main(String[] args) {
		SpringApplication.run(MatterBot.class, args)
	}
}
