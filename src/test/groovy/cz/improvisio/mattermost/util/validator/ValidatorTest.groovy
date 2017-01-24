package cz.improvisio.mattermost.util.validator

import spock.lang.Specification
import spock.lang.Unroll

class ValidatorTest extends Specification {
	void setup() {

	}

	void cleanup() {

	}

	@Unroll
	def "Test validation"() {
		given:
			def validatable = new ValidatableObj()
			validatable.with {
				alpha = alphaVal
				num = numVal
				alphanum = alphanumVal
				maxSize = maxSizeVal
				minSize = minSizeVal
			}

		expect:
			validatable.isValid() == isValid && validatable.validationErrors == valErrs

		where:
			alphaVal | numVal   | alphanumVal  | maxSizeVal     | minSizeVal | isValid | valErrs
			""       | ""       | ""           | ""             | "ab"       | true    | []
			"abcABC" | "123"    | "abcABC123"  | "123456789"    | "a.c"      | true    | []
			"abc"    | "123456" | "123ABCabc"  | "12345"        | "12"       | true    | []
			"ab12"   | "123"    | "abcABC123"  | "123456789"    | "aBc"      | false   | ["alpha"]
			"abcABC" | "a"      | "abcABC123"  | "123456789"    | "abc"      | false   | ["num"]
			"abcABC" | "123"    | "ac_d12"     | "123456789"    | "a1c"      | false   | ["alphanum"]
			"abcABC" | "123"    | "abcABC123"  | "123456789123" | "abc"      | false   | ["maxSize"]
			"abcABC" | "123"    | "abcABC123"  | "123456789"    | "x"        | false   | ["minSize"]
			"1a"     | "123"    | "abcABC123"  | "123456789"    | "1"        | false   | ["alpha", "minSize"]
			"abcABC" | "123a"   | "abcABC123"  | "123456789123" | "abc"      | false   | ["num", "maxSize"]
			"abcABC" | "123"    | "abcAB.C123" | "123456789"    | "."        | false   | ["alphanum", "minSize"]
	}

	class ValidatableObj implements Validatable {
		@Require({ it ==~ /^[A-Za-z]*$/ })
		String alpha

		@Require({ it ==~ /^[0-9]*$/ })
		String num

		@Require({ it ==~ /^[A-Za-z0-9]*$/ })
		String alphanum

		@Require({ it?.length() <= 10 })
		String maxSize

		@Require({ it?.length() >= 2 })
		String minSize
	}
}
