package com.github.notyy.typeflow.editor

import com.github.notyy.typeflow.domain.{Aliyun, CodeUri, Local, ModelFilePath, OutputPath, PackageName, Platform}
import com.github.notyy.typeflow.editor.codegenerations.{GenAliyunHandler, GenAliyunHttpInputEndpointHandler, GenAliyunOSSOutputEndpoint, GenAliyunOSSOutputEndpoints, GenAliyunlCallStatement, GenCommandLineInputEndpoint, GenCommandLineInputEndpoints, GenFileInputEndpoint, GenFileInputEndpoints, GenFileOutputEndpoint, GenFileOutputEndpoints, GenFormalParams, GenJSonParamType, GenJSonParamType4InputEndpoint, GenJavaOutputEndpoint, GenJavaPureFunction, GenLocalCallStatement, GenOutputEndpoints, GenPlatformHandlers, GenPureFunctions, GenWriteOut}
import com.github.notyy.typeflow.editor.codetemplates.{LoadAliyunHandlerCodeTemplate, LoadAliyunHttpInputEndpointCodeTemplate, LoadAliyunOSSOutputEndpointCodeTemplate, LoadCommandLineInputEndpointCodeTemplate, LoadFileInputEndpointCodeTemplate, LoadFileOutputEndpointCodeTemplate, LoadOutputEndpointCodeTemplate, LoadPureFunctionCodeTemplate}
import com.typesafe.scalalogging.Logger

import scala.util.{Failure, Success, Try}

object GenCodeScript extends App {
  private val logger = Logger(GenCodeScript.getClass)
  if (args.length != 6) {
    println("usage: genCode {modelFilePath} {outputPath} {lang} {packageName} {platform} {codeUri}")
    System.exit(1)
  }
  val (modelPath, codeLang, outputPath, packageName, platform, codeUri) =
    execute(modelFilePath = args(0), outputPath = args(1), lang = args(2),
      packageName = args(3), platform = args(4), codeUri = args(5))
  val result = ReadFile.execute(modelPath).map { puml =>
    val model = PlantUML2Model.execute(ModelPath2ModelName.execute(modelPath.value), puml)
    val (pureFunctions, inputEndpoints, outputEndpoints, customTypes, fileOutputEndpoints, aliyunOSSOutputEndpoints) = DefinitionSorter.execute(model)
    val genPureFunctions = new GenPureFunctions(new GenJavaPureFunction(new GenFormalParams))
    val genCommandLineInputEndpoints = new GenCommandLineInputEndpoints(new GenCommandLineInputEndpoint(new GenCallingChain(new GenLocalCallStatement)))
    val genFileInputEndpoints = new GenFileInputEndpoints(new GenFileInputEndpoint(new GenCallingChain(new GenLocalCallStatement)))
    val genOutputEndpoints = new GenOutputEndpoints(new GenJavaOutputEndpoint(new GenFormalParams))
    val genFileOutputEndpoints = new GenFileOutputEndpoints(new GenFileOutputEndpoint(new GenFormalParams))
    val genAliyunOSSOutputEndpoints = new GenAliyunOSSOutputEndpoints(new GenAliyunOSSOutputEndpoint(new GenFormalParams, new GenWriteOut))
    val saveCodes = new SaveCodes(new SaveToFile, new QualifiedName2CodeStructurePath)

    val pureFunctionSaveRs = LoadPureFunctionCodeTemplate.execute(JAVA_LANG).flatMap { javaPureFunctionCodeTemplate =>
      val pureFunctionCodes = genPureFunctions.execute(codeLang, pureFunctions, packageName, javaPureFunctionCodeTemplate)
      saveCodes.execute(pureFunctionCodes, outputPath)
    }

    val (commandLineArgsInputEndpoints, commandLineInputEndpoints, aliyunHttpInputEndpoints, fileInputEndpoints) = InputEndpointSorter.execute(inputEndpoints)
    val commandLineInputEndpointSaveRs = LoadCommandLineInputEndpointCodeTemplate.execute(SCALA_LANG).flatMap { scalaCommandLineInputEndpointCodeTemplate =>
      val commandLineInputEndpointCodes = genCommandLineInputEndpoints.execute(commandLineInputEndpoints, packageName, scalaCommandLineInputEndpointCodeTemplate, model)
      saveCodes.execute(commandLineInputEndpointCodes, outputPath)
    }
    commandLineInputEndpointSaveRs match {
      case Success(_) => logger.info("commandLineInputEndpoint save successfully")
      case Failure(exception) => logger.error("commandLineInputEndpoint save failed", exception)
    }

    val fileInputEndpointSaveRs = LoadFileInputEndpointCodeTemplate.execute(SCALA_LANG).flatMap { scalaFileInputEndpointCodeTemplate =>
      val fileInputEndpointCodes = genFileInputEndpoints.execute(fileInputEndpoints, packageName, scalaFileInputEndpointCodeTemplate, model)
      saveCodes.execute(fileInputEndpointCodes, outputPath)
    }
    fileInputEndpointSaveRs match {
      case Success(_) => logger.info("fileInputEndpoint save successfully")
      case Failure(exception) => logger.error("fileInputEndpoint save failed", exception)
    }

    val outputEndpointSaveRs = LoadOutputEndpointCodeTemplate.execute(JAVA_LANG).flatMap { javaOutputEndpointCodeTemplate =>
      val outputEndpointCodes = genOutputEndpoints.execute(JAVA_LANG, outputEndpoints, packageName, javaOutputEndpointCodeTemplate)
      saveCodes.execute(outputEndpointCodes, outputPath)
    }

    val fileOutputEndpointSaveRs = LoadFileOutputEndpointCodeTemplate.execute(JAVA_LANG).flatMap { fileOutputEndpointCodeTemplate =>
      val fileOutputEndpointCodes = genFileOutputEndpoints.execute(JAVA_LANG, fileOutputEndpoints, packageName, fileOutputEndpointCodeTemplate)
      saveCodes.execute(fileOutputEndpointCodes, outputPath)
    }

    if(platform != Local) {
      val platformCodesRs = LoadAliyunHandlerCodeTemplate.execute().flatMap {
        aliyunHandlerCodeTemplate => {
          LoadAliyunHttpInputEndpointCodeTemplate.execute().flatMap {
            aliyunHttpInputEndpointCodeTemplate => {
              LoadAliyunOSSOutputEndpointCodeTemplate.execute(SCALA_LANG).map { aliyunOSSOutputEndpointCodeTemplate =>
                val genPlatformHandlers = new GenPlatformHandlers(new GenAliyunHandler(new GenJSonParamType, new GenWriteOut), new GenAliyunHttpInputEndpointHandler(new GenJSonParamType4InputEndpoint, new GenCallingChain(new GenAliyunlCallStatement(model.name))),new GenAliyunOSSOutputEndpoint(new GenFormalParams,new GenWriteOut))
                genPlatformHandlers.execute(platform, aliyunHandlerCodeTemplate, aliyunHttpInputEndpointCodeTemplate,aliyunOSSOutputEndpointCodeTemplate, packageName, model)
              }
            }
          }
          }.flatMap { platformCodes =>
          saveCodes.execute(platformCodes, outputPath)
        }
      }
      platformCodesRs match {
        case Success(_) => println("platform code generation successfully")
        case Failure(exception) => {
          logger.error(s"error when generating code for ${modelPath.value} ", exception)
        }
      }

//      val aliyunOSSOutputEndpointSaveRs = LoadAliyunOSSOutputEndpointCodeTemplate.execute(SCALA_LANG).flatMap { aliyunOSSOutputEndpointCodeTemplate =>
//        val aliyunOSSOutputEndpointCodes = genAliyunOSSOutputEndpoints.execute(SCALA_LANG, aliyunOSSOutputEndpoints, packageName, aliyunOSSOutputEndpointCodeTemplate)
//        saveCodes.execute(aliyunOSSOutputEndpointCodes, outputPath)
//      }
//      aliyunOSSOutputEndpointSaveRs match {
//        case Success(_) => println("platform code generation successfully")
//        case Failure(exception) => {
//          logger.error(s"error when generating code for ${modelPath.value} ", exception)
//        }
//      }

      GenAliyunTemplate.execute(model.name,model.definitions, codeUri, packageName, outputPath)
    }

    val totalRs: Vector[Try[Unit]] = Vector(pureFunctionSaveRs, commandLineInputEndpointSaveRs, outputEndpointSaveRs, fileOutputEndpointSaveRs)
    if (totalRs.exists(_.isFailure)) {
      totalRs.filter(_.isFailure).map(_.asInstanceOf[Failure[Unit]]).reduce {
        (f1, f2) => Failure(new IllegalArgumentException(s"${f1.exception.getMessage}${System.lineSeparator()}${f2.exception.getMessage}", f1.exception))
      }
    } else {
      Success(())
    }
  }

  result match {
    case Success(_) => {
      println("code generation successfully")
    }
    case Failure(exception) => {
      logger.error(s"error when generating code for ${modelPath.value} ", exception)
    }
  }

  def execute(modelFilePath: String, outputPath: String, lang: String, packageName: String, platform: String, codeUri: String): (ModelFilePath, CodeLang, OutputPath, PackageName, Platform, CodeUri) = {
    val platformEnum = platform match {
      case "local" => Local
      case "aliyun" => Aliyun
    }
    (ModelFilePath(modelFilePath), CodeLang.from(lang), OutputPath(outputPath), PackageName(packageName), platformEnum, CodeUri(codeUri))
  }
}
