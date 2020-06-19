package com.github.notyy.typeflow.editor.codetemplates

import com.github.notyy.typeflow.editor.{CodeTemplate, CodeTemplatePath, ReadFile}

import scala.util.Try

object LoadAliyunHttpInputEndpointCodeTemplate {
  def execute(): Try[CodeTemplate] = {
    val path: CodeTemplatePath = CodeTemplatePath("./code_template/scala/AliyunHttpInputEndpoint.scala")
    ReadFile.execute(path).map(CodeTemplate)
  }
}
