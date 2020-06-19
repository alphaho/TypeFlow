package com.github.notyy.typeflow.editor.codegenerations

import com.github.notyy.typeflow.domain.AliyunOSSOutputEndpoint
import com.github.notyy.typeflow.editor._

class GenAliyunOSSOutputEndpoints(private val genAliyunOSSOutputEndpoint: GenAliyunOSSOutputEndpoint) {
  def execute(codeLang: CodeLang, aliyunOSSOutputEndpoints: Vector[AliyunOSSOutputEndpoint], packageName: PackageName, codeTemplate: CodeTemplate): Vector[ScalaCode] = {
    codeLang match {
      case JAVA_LANG => ???
      case SCALA_LANG => aliyunOSSOutputEndpoints.map(oe => genAliyunOSSOutputEndpoint.execute(packageName, oe, codeTemplate))
    }
  }
}
