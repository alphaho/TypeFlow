package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.{CodeDir, QualifiedName}

object QualifiedName2CodeDir {
  def execute(qualifiedName: QualifiedName): CodeDir = {
    CodeDir(qualifiedName.value.split('.').init.reduce((s1,s2) =>s"$s1/$s2"))
  }
}
