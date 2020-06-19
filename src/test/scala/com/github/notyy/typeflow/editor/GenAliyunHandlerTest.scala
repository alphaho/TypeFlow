package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.{ModelFilePath, PackageName}
import com.github.notyy.typeflow.editor.codegenerations.{GenAliyunHandler, GenJSonParamType, GenWriteOut}
import com.github.notyy.typeflow.editor.codetemplates.LoadAliyunHandlerCodeTemplate
import org.scalatest.{FunSpec, Matchers}

class GenAliyunHandlerTest extends FunSpec with Matchers {
  describe("GenAliyunHandler") {
    it("can generate aliyun handler code for definitions") {
      val puml = ReadFile.execute(ModelFilePath("./fixtures/diff/newModel.puml")).get
      val model = PlantUML2Model.execute("newModel", puml)
      val add2 = model.definitions.find(_.name == "Add2").get
      val genAliyunHandler = new GenAliyunHandler(new GenJSonParamType, new GenWriteOut)
      val codeTemplate = LoadAliyunHandlerCodeTemplate.execute().get
      val scalaCode = genAliyunHandler.execute(PackageName("com.github.notyy.calculator"),add2,codeTemplate)
      println(scalaCode.content)
    }
  }
}
