package com.github.notyy.typeflow.editor.codetemplates

import com.github.notyy.typeflow.domain.{CodeTemplate, CodeTemplatePath}
import com.github.notyy.typeflow.editor.{CodeLang, ReadFile}

import scala.util.Try

object LoadCommandLineInputEndpointCodeTemplate {
  def execute(codeLang: CodeLang): Try[CodeTemplate] = {
    val path: CodeTemplatePath = codeLang match {
      case _ => CodeTemplatePath("./code_template/scala/CommandLineInputEndpoint.scala")
    }
    ReadFile.execute(path).map(CodeTemplate)
  }
}
