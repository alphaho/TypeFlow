package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.CommandLineInputEndpoint
import org.scalatest.{FunSpec, Matchers}

class GenCommandLineInputEndpointTest extends FunSpec with Matchers {
  describe("GenCommandLineInputEndpoint") {
    it("will generate command line input endpoint will it's calling chain") {
      val puml = ReadFile.execute(Path("./fixtures/diff/newModel.puml")).get
      val model = PlantUML2Model.execute("newModel", puml)
      val genCommandLineInputEndpoint = new GenCommandLineInputEndpoint(model)
      val numInput = model.definitions.find(_.name == "NumInput").get.asInstanceOf[CommandLineInputEndpoint]
      val codeTemplate = LoadInputEndpointCodeTemplate.execute(SCALA_LANG).get
      val code = genCommandLineInputEndpoint.execute(PackageName("com.github.notyy.typeflow.editor"),numInput, codeTemplate)
      //print the code to review, for now...
      println(code)
    }
  }
}