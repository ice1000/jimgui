package test

import org.ice1000.jimgui.JImStyleVars
import org.ice1000.jimgui.JImVec4
import org.ice1000.jimgui.MutableJImVec4
import org.ice1000.jimgui.cpp.DeallocatableObjectManager
import org.ice1000.jimgui.dsl.JImGuiContext
import org.ice1000.jimgui.dsl.runPer
import org.ice1000.jimgui.dsl.treeNode
import org.ice1000.jimgui.util.JniLoader
import org.lice.core.SymbolList
import org.lice.model.ExpressionNode
import org.lice.model.Node
import org.lice.model.SymbolNode
import org.lice.model.ValueNode
import org.lice.parse.Lexer
import org.lice.parse.Parser
import java.awt.Color

fun fromAWT(color: Color) = MutableJImVec4(color.red / 256f,
    color.green / 256f,
    color.blue / 256f,
    color.alpha / 256f)

fun main() {
  JniLoader.load()
  val sourceCode = JImGuiContext::class.java
      .getResource("/bizarre.lice").readText()
  val sourceCodeLines = sourceCode.split(System.lineSeparator())
  val liceAST = Parser
      .parseTokenStream(Lexer(sourceCode))
      .accept(SymbolList())
  val keywords = fromAWT(Color.decode("#CC7832"))
  val strings = fromAWT(Color.decode("#6A8759"))
  val numbers = fromAWT(Color.decode("#6897BB"))
  val manager = DeallocatableObjectManager(listOf(keywords, strings, numbers))
  val codeColors = arrayOfNulls<JImVec4>(sourceCode.length)
  val eolLen = System.lineSeparator().length

  fun colorizeNode(root: Node, color: JImVec4) {
    val metaData = root.meta
    val startOffset = sourceCodeLines
        .subList(0, metaData.beginLine - 1)
        .sumBy { it.length } + metaData.beginIndex + (metaData.beginLine - 2) * eolLen
    val endOffset = sourceCodeLines
        .subList(0, metaData.endLine - 1)
        .sumBy { it.length } + metaData.endIndex - 1 + (metaData.endLine - 2) * eolLen
    for (offset in startOffset..endOffset) codeColors[offset] = color
  }

  fun colorizeAST(root: Node) {
    when (root) {
      is ValueNode -> {
        val value = root.eval()
        if (value is String) colorizeNode(root, strings)
        else if (value is Number) colorizeNode(root, numbers)
      }
      is ExpressionNode -> {
        colorizeAST(root.node)
        root.params.forEach(::colorizeAST)
      }
      is SymbolNode -> if (root.name.startsWith("def") ||
          root.name == "lazy" ||
          root.name == "if" ||
          root.name == "while" ||
          root.name == "when" ||
          root.name == "lambda" ||
          root.name == "expr")
        colorizeNode(root, keywords)
    }
  }
  colorizeAST(liceAST)

  runPer(15) {
    "Lice AST" { displayAST(liceAST, this) }
    "Source Code" {
      pushStyleVar(JImStyleVars.ItemSpacing, 0f, 2f)
      var tokenStart = 0
      var attributeSet = codeColors[0]
      codeColors[0] = null
      for (i in codeColors.indices) {
        val isEol = sourceCode[i] == '\n'
        if (attributeSet != codeColors[i] || isEol) {
          val token = sourceCode.substring(tokenStart, i)
          if (attributeSet != null)
            textColored(attributeSet, token)
          else text(token)
          if (attributeSet === keywords && isItemHovered)
            setTooltip("Keyword: $token")
          if (!isEol) sameLine()
          tokenStart = if (isEol) i + eolLen else i
          attributeSet = codeColors[i]
        }
      }
      popStyleVar()
    }
  }
  manager.deallocateAll()
}

fun displayAST(root: Node, imgui: JImGuiContext): Unit = when (root) {
  is ValueNode -> imgui.text(root.toString())
  is SymbolNode -> imgui.text(root.name)
  is ExpressionNode -> imgui.treeNode("${root.node}") {
    root.params.forEach { displayAST(it, imgui) }
  }
  else -> {
  }
}
