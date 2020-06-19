package com.github.notyy.typeflow.editor.codetemplates

import com.github.notyy.typeflow.editor.{CodeTemplate, CodeTemplatePath, ReadFile}

import scala.util.Try

object LoadAliyunHandlerCodeTemplate {
  def execute(): Try[CodeTemplate] = {
    val path: CodeTemplatePath = CodeTemplatePath("./code_template/scala/StreamRequestHandlerTemplate.scala")
    ReadFile.execute(path).map(CodeTemplate)
  }
}
