package cz.improvisio.mattermost.util.validator

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Adapted from https://gist.github.com/tolitius/1992123
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Require {
	Class value()
}
