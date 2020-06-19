package com.github.notyy.typeflow.editor.codegenerations

import com.github.notyy.typeflow.domain.{CodeTemplate, JavaCode, OutputEndpoint, PackageName}
import com.github.notyy.typeflow.editor._

class GenOutputEndpoints(private val genJavaOutputEndpoint: GenJavaOutputEndpoint) {
  def execute(codeLang: CodeLang, outputEndpoints: Vector[OutputEndpoint], packageName: PackageName, codeTemplate: CodeTemplate): Vector[JavaCode] = {
    codeLang match {
      case JAVA_LANG => outputEndpoints.map(oe => genJavaOutputEndpoint.execute(packageName, oe, codeTemplate))
      case SCALA_LANG => ???
    }
  }
}
