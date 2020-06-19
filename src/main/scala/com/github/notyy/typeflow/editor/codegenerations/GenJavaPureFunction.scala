package com.github.notyy.typeflow.editor.codegenerations

import com.github.notyy.typeflow.domain
import com.github.notyy.typeflow.domain.{CodeTemplate, JavaCode, PackageName, PureFunction, QualifiedName}

class GenJavaPureFunction(val genFormalParams: GenFormalParams) {
  def execute(packageName: PackageName, pureFunction: PureFunction, codeTemplate: CodeTemplate): JavaCode = {
    val code = codeTemplate.value.replaceAllLiterally("$PackageName$", packageName.value).
      replaceAllLiterally("$DefinitionName$", pureFunction.name).
      replaceAllLiterally("$ReturnType$", genReturnType(pureFunction)).
      replaceAllLiterally("$Params$", genFormalParams.execute(pureFunction.inputs))
    domain.JavaCode(QualifiedName(s"${packageName.value}.${pureFunction.name}"), code)
  }

  private def genReturnType(pureFunction: PureFunction): String = {
    val outputs = pureFunction.outputs.filterNot(_.outputType.name == "Unit")
    if(outputs.size == 1) {
      outputs.head.outputType.name
    } else {
      s"Tuple${outputs.size}<${outputs.map(_.outputType.name).mkString(",")}>"
    }
  }
}
