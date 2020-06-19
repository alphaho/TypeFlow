package com.github.notyy.typeflow.editor.codetemplates

import com.github.notyy.typeflow.domain.{CodeTemplate, CodeTemplatePath}
import com.github.notyy.typeflow.editor._

import scala.util.Try

object LoadAliyunOSSOutputEndpointCodeTemplate {
  def execute(codeLang: CodeLang): Try[CodeTemplate] = {
    val path: CodeTemplatePath = codeLang match {
      case JAVA_LANG => ???
      case SCALA_LANG => CodeTemplatePath("./code_template/scala/AliyunOSSOutputEndpointTemplate.scala")
    }
    ReadFile.execute(path).map(CodeTemplate)
  }
}
