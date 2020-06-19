package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.CodeTemplate
import com.github.notyy.typeflow.editor.codetemplates.LoadFileInputEndpointCodeTemplate
import org.scalatest.{FunSpec, Matchers}

class LoadCommandLineInputEndpointCodeTemplateTest extends FunSpec with Matchers {
  describe("LoadInputEndpointCodeTemplate") {
    it("can load command line input endpoint code template") {
      val codeTemplate: CodeTemplate = LoadFileInputEndpointCodeTemplate.execute(JAVA_LANG).get
      codeTemplate.value.isEmpty shouldNot be(true)
    }
  }
}
