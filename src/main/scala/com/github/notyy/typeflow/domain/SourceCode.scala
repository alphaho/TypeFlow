package com.github.notyy.typeflow.domain

trait SourceCode {
  def qualifiedName: QualifiedName
  def content: String
}
