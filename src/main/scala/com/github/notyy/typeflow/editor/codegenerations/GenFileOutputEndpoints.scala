package com.github.notyy.typeflow.editor.codegenerations

import com.github.notyy.typeflow.domain.FileOutputEndpoint
import com.github.notyy.typeflow.editor._

class GenFileOutputEndpoints(private val genFileOutputEndpoint: GenFileOutputEndpoint) {
  def execute(codeLang: CodeLang, fileOutputEndpoints: Vector[FileOutputEndpoint], packageName: PackageName, codeTemplate: CodeTemplate): Vector[JavaCode] = {
    codeLang match {
      case JAVA_LANG => fileOutputEndpoints.map(oe => genFileOutputEndpoint.execute(packageName, oe, codeTemplate))
      case SCALA_LANG => ???
    }
  }
}
