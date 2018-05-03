import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input

/**
 * @author ice1000
 * @since v0.1
 */
class GenTask extends DefaultTask {
	@Input
	def className

	@Input
	def targetJavaFile

	@Input
	def targetCppFile
}
