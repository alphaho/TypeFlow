package com.github.notyy.typeflow.editor.codegenerations

import com.github.notyy.typeflow.domain.{CodeTemplate, CommandLineInputEndpoint, Model, PackageName, ScalaCode}

class GenCommandLineInputEndpoints(private val genCommandLineInputEndpoint: GenCommandLineInputEndpoint) {
  def execute(commandLineInputEndpoints: Vector[CommandLineInputEndpoint], packageName: PackageName, codeTemplate: CodeTemplate, model: Model): Vector[ScalaCode] = {
    commandLineInputEndpoints.map(cie => genCommandLineInputEndpoint.execute(packageName, cie, codeTemplate, model))
  }
}
