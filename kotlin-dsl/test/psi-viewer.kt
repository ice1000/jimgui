package test

import org.ice1000.jimgui.dsl.runPer
import org.ice1000.jimgui.dsl.treeNode
import org.ice1000.jimgui.util.JniLoader

fun main() {
	JniLoader.load()
	runPer(15) {
		"Psi Viewer" {
			treeNode("PsiFile") {
				treeNode("PsiImportStatement") {
					text("PsiLeafElement")
					text("PsiJavaReferenceExpression")
				}
				treeNode("PsiImportStatement") {
					text("PsiLeafElement")
					text("PsiJavaReferenceExpression")
				}
				treeNode("PsiClass") {
					text("PsiLeafElement")
					text("PsiIdentifier")
					text("PsiLeafElement")
					treeNode("PsiClassBody") {
						treeNode("PsiConstructor") {
							text("PsiLeafElement")
							text("PsiIdentifier")
						}
						treeNode("PsiMethod") {
							text("PsiAnnotation")
							text("PsiLeafElement")
							text("PsiIdentifier")
						}
					}
					text("PsiLeafElement")
				}
			}
		}
	}
}
