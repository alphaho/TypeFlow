package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.{CodeStructurePath, QualifiedName}

class QualifiedName2CodeStructurePath {
  def execute(qualifiedName: QualifiedName, codeLang: CodeLang): CodeStructurePath = {
    CodeStructurePath(s"${qualifiedName.value.replaceAllLiterally(".", "/")}.${CodeLang.str(codeLang)}")
  }
}
