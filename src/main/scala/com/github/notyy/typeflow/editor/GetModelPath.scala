package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.{OutputPath, Path}

object GetModelPath {
  def execute(modelName: String): Path = OutputPath(s"./localoutput/${modelName}.typeflow")
}
