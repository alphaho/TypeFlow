package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.WrappedOutput

import scala.util.{Success, Try}

object CommandLineOutputEndpoint {
  def execute(output: WrappedOutput):Try[Unit] = {
    CommandLineUI.onResponse(output.value)
    Success(())
  }
}
