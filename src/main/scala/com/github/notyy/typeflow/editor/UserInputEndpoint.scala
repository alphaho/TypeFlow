package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.UserInput

import scala.io.StdIn

object UserInputEndpoint {
  def execute(): UserInput = {
    UserInput(StdIn.readLine())
  }
}
