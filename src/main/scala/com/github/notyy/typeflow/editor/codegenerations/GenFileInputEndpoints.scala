package com.github.notyy.typeflow.editor.codegenerations

import com.github.notyy.typeflow.domain.{FileInputEndpoint, Model}
import com.github.notyy.typeflow.editor.{CodeTemplate, PackageName, ScalaCode}

class GenFileInputEndpoints(private val genFileInputEndpoint: GenFileInputEndpoint) {
  def execute(fileInputEndpoints: Vector[FileInputEndpoint], packageName: PackageName, codeTemplate: CodeTemplate, model: Model): Vector[ScalaCode] = {
    fileInputEndpoints.map(fie => genFileInputEndpoint.execute(packageName, fie, codeTemplate, model))
  }
}
