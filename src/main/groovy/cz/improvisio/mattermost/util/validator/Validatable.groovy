package cz.improvisio.mattermost.util.validator

/**
 * @author OsaSoft https://github.com/OsaSoft
 */
trait Validatable {
	static Validator validator = new Validator()

	def validationErrors = []
	boolean isValid() {
		validator.isValid(this)
	}
}
