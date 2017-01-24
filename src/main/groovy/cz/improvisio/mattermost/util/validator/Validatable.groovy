package cz.improvisio.mattermost.util.validator

trait Validatable {
	static Validator validator = new Validator()

	def validationErrors = []
	boolean isValid() {
		validator.isValid(this)
	}
}
