package cz.improvisio.mattermost.dataTypes

/**
 * Every class with this Trait must have a log object! Use @Slf4j annotation
 * @author OsaSoft https://github.com/OsaSoft
 */
trait IgnoreUnknownPropertiesTrait {
	def propertyMissing(String name, value) {
		log.warn "Trying to set nonexisting property '$name:$value' in '${this.class.name}'"
		//do nothing
	}
}