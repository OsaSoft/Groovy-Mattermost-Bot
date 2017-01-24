package cz.improvisio.mattermost.util.validator

import org.springframework.stereotype.Component

/**
 * Adapted from https://gist.github.com/tolitius/1992123
 *
 * Might turn this into a separate mini-library
 */
class Validator {
	Validator() {
	}

	def isValid(Validatable pogo) {
		boolean valid = true
		pogo.getClass().declaredFields.each {valid &= isValidField(pogo, it)}
		valid
	}

	private isValidField(Validatable pogo, field) {
		def annotation = field.getAnnotation(Require)
		!annotation || meetsConstraint(pogo, field, annotation.value())
	}

	private meetsConstraint(Validatable pogo, field, constraint) {
		def closure = constraint.newInstance(null, null)
		field.setAccessible(true)
		def res = closure.call(field.get(pogo))
		if (!res) {
			pogo.validationErrors.add(field.name)
		}

		res
	}
}
