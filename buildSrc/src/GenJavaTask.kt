package org.ice1000.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.intellij.lang.annotations.Language

open class GenJavaTask(
    @Input val className: String,
    @Input val since: String = "v0.1",
    @Input private val packageName: String = "org.ice1000.jimgui",
    relativePath: String = packageName.replace('.', '/'),
) : DefaultTask() {
  @OutputFile val targetJavaFile = project
      .projectDir
      .resolve("gen")
      .resolve(relativePath)
      .resolve("$className.java")
      .absoluteFile

  init {
    group = "code generation"
    targetJavaFile.parentFile.mkdirs()
  }

  @Language("Text") @Internal
  open val userCode = """/** package-private by design */
  $className() { }
"""

  @get:Internal protected val prefixJava
    @Language("JAVA", suffix = "}")
    get() = """package $packageName;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.*;
import org.ice1000.jimgui.cpp.*;
import org.intellij.lang.annotations.*;
import org.jetbrains.annotations.*;
import java.nio.charset.StandardCharsets;

import static org.ice1000.jimgui.util.JImGuiUtil.*;

/**
 * @author ice1000
 * @since $since
 */
@SuppressWarnings("ALL")

public class $className {
$userCode
"""

  @get:Internal protected val prefixInterfacedJava
    @Language("JAVA", suffix = "}")
    get() = """package $packageName;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.*;
import org.ice1000.jimgui.cpp.*;
import org.intellij.lang.annotations.*;
import org.jetbrains.annotations.*;
import java.nio.charset.StandardCharsets;

import static org.ice1000.jimgui.util.JImGuiUtil.*;

/**
 * @author ice1000
 * @since $since
 */
@SuppressWarnings("ALL")

public interface $className {
"""

  @Internal val eol: String = System.lineSeparator()
}
