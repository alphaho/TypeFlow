package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.{Content, ModelFilePath, OutputPath}
import org.scalatest.{FunSpec, Matchers}

class DiffTest extends FunSpec with Matchers {
  describe("Diff") {
    it("can compare two PlantUML and produce a new PlantUML that highlight the differences") {
      val newModel = ReadFile.execute(ModelFilePath("./fixtures/diff/newModel.puml")).get
      val multi_param = ReadFile.execute(ModelFilePath("./fixtures/diff/multi_param.puml")).get
      val diff = Diff.execute(multi_param, newModel)
      new SaveToFile().execute(OutputPath("./localoutput/diff/diff.puml"),Content(diff))
    }
  }
}
