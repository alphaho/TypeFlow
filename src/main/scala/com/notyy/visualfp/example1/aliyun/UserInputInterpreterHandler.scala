package com.notyy.visualfp.example1.aliyun

import java.io.{InputStream, OutputStream}

import com.aliyun.fc.runtime.{Context, StreamRequestHandler}
import com.notyy.visualfp.example1.UserInputInterpreter
import com.notyy.visualfp.util.{JSONUtil, JSonFormats}

import scala.io.Source

class UserInputInterpreterHandler extends StreamRequestHandler{
  override def handleRequest(input: InputStream, output: OutputStream, context: Context): Unit = {
    val inStr = Source.fromInputStream(input).mkString
    val rs = UserInputInterpreter.execute(inStr)
    output.write(JSONUtil.toJSON(rs,JSonFormats.userInterpreterResultFormats).getBytes)
  }
}
