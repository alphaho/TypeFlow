package com.github.notyy.typeflow.editor.codegenerations

import com.github.notyy.typeflow.domain.PureFunction
import com.github.notyy.typeflow.editor._

class GenPureFunctions(val genJavaPureFunction: GenJavaPureFunction) {
  def execute(codeLang: CodeLang, pureFunctions: Vector[PureFunction],packageName: PackageName, codeTemplate: CodeTemplate): Vector[JavaCode] = {
    codeLang match {
      case JAVA_LANG => pureFunctions.map(pf => genJavaPureFunction.execute(packageName, pf, codeTemplate))
      case SCALA_LANG => ???
    }
  }
}
