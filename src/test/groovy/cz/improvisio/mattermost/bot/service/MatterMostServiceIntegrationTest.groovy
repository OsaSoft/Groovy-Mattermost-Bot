package cz.improvisio.mattermost.bot.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Be careful with rate limits while running these tests!
 */
@SpringBootTest
class MatterMostServiceIntegrationTest extends Specification {
	@Shared testUsername = "bot@bot.com"
	@Shared testPassword = "password123"
	@Shared testTeam = "khnf6a1udiysty6uphfxa3yzqr"
	@Shared testChannel = "fh76k1317bncpb8yismnrsokeh"

	@Autowired
	MatterMostService service

	void setup() {
	}

	void cleanup() {

	}

	def "login returns proper token and user with correct login data"() {
		when:
			def result = service.login(testUsername, testPassword)
		then:
			result
		and:
			result.token
		and:
			result.user
	}

	def "login errors"() {
		when:
			def result = service.login("wrongName", "wrongPass")
		then:
			!result
	}

	def "send message works"() {
		given:
			def token = service.login(testUsername, testPassword).token

		expect:
			service.sendMessage(token, testTeam, testChannel, "Test mate!")
	}

	@Unroll
	def "send message fails to nonexisting team/channel=#team/#channel"() {
		given:
			def token = service.login(testUsername, testPassword).token

		expect:
			!service.sendMessage(token, team, channel, "Shouldnt see this!")

		where:
			team		| channel
			testTeam	| "noChannel"
			"noTeam"	| testChannel
			"noTeam"	| "noChannel"
	}
}
