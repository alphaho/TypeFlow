package com.github.notyy.typeflow.editor.codegenerations

import com.github.notyy.typeflow.domain
import com.github.notyy.typeflow.domain.{CodeTemplate, JavaCode, OutputEndpoint, PackageName, QualifiedName}

class GenJavaOutputEndpoint(val genFormalParams: GenFormalParams) {
  def execute(packageName: PackageName, outputEndpoint: OutputEndpoint, codeTemplate: CodeTemplate): JavaCode = {
    val code = codeTemplate.value.replaceAllLiterally("$PackageName$", packageName.value).
      replaceAllLiterally("$DefinitionName$", outputEndpoint.name).
      replaceAllLiterally("$ReturnType$", replaceEmptyReturnTypeWithVoid(outputEndpoint.outputs.head.outputType.name)).
      replaceAllLiterally("$Params$", genFormalParams.execute(outputEndpoint.inputs))
    domain.JavaCode(QualifiedName(s"${packageName.value}.${outputEndpoint.name}"), code)
  }

  def replaceEmptyReturnTypeWithVoid(returnType: String): String = {
    if (returnType == "Unit") "void" else returnType
  }
}
