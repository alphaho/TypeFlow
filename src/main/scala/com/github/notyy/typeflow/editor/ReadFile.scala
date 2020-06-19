package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.Path

import scala.io.Source
import scala.util.Try

object ReadFile {
  def execute(path: Path): Try[String] = Try {
    val source = Source.fromFile(path.value)
    val rs = source.mkString
    source.close()
    rs
  }
}
