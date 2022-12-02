package day0

import java.io.*
import java.nio.file.Paths
import scala.io.Source

class PuzzleFiles(day: String) {
  private def inputFolderPath = Paths.get("src", "resources", s"day$day")

  private val input1 = "input1.txt"
  private val exampleInput1 = "input1-example.txt"
  private val input2 = "input2.txt"
  private val exampleInput2 = "input2-example.txt"

  def getLinesFromExampleInput1: List[String] = getLinesFrom(exampleInput1)

  def getLinesFromInput1: List[String] = getLinesFrom(input1)

  def getLinesFromExampleInput2: List[String] = getLinesFrom(exampleInput2)

  def getLinesFromInput2: List[String] = getLinesFrom(input2)

  private def getPathFor(file: String): String = inputFolderPath.resolve(file).toString

  private def getLinesFrom(input: String): List[String] = {
    val source = Source.fromFile(getPathFor(input))
    try source.getLines().toList finally source.close()
  }

  def createInputFiles: List[String] = {
    val folder = inputFolderPath.toString
    new File(folder).mkdir

    List[String](input1, exampleInput1, input2, exampleInput2)
      .map(f => Paths.get(folder, f).toString)
      .map(f => {
        val inputFile = new File(f)
        inputFile.createNewFile
        inputFile.getAbsolutePath
      })
  }

  def createSources: String = {
    val srcScalaFolder = Paths.get("src", "main", "scala").toString
    val dayFolderInSrc = Paths.get(srcScalaFolder, s"day$day").toString
    new File(dayFolderInSrc).mkdir

    val solution =
      readFile(Paths.get(srcScalaFolder, "day0", "Puzzle.scala").toString)
        .map(line => line.replace("package day0", s"package day$day")
          .replace("class Puzzle", s"class PuzzleDay$day"))

    val solutionFile = new File(Paths.get(dayFolderInSrc, s"PuzzleDay$day.scala").toString)
    writeFile(solution, solutionFile)
    solutionFile.getAbsolutePath
  }

  def createTests: String = {
    val testScalaFolder = Paths.get("src", "test", "scala").toString
    val dayFolderInTest = Paths.get(testScalaFolder, s"day$day").toString
    new File(dayFolderInTest).mkdir

    val solution = readFile(Paths.get(testScalaFolder, "day0", "PuzzleDay0Tests.scala").toString)
      .map(line => line.replace("package day0", s"package day$day")
        .replace("class PuzzleDay0Tests", s"class PuzzleDay${day}Tests")
        .replace("val day = \"0\"", s"val day = \"$day\"")
        .replace("val puzzle = new Puzzle", s"val puzzle = new PuzzleDay$day"))

    val solutionFile = new File(Paths.get(dayFolderInTest, s"PuzzleDay${day}Tests.scala").toString)
    writeFile(solution, solutionFile)
    solutionFile.getAbsolutePath
  }

  private def readFile(filepath: String): List[String] = {
    val source = Source.fromFile(Paths.get(filepath).toString)
    val solution = try source.getLines().toList finally source.close()
    solution
  }

  private def writeFile(solution: List[String], solutionFile: File): Unit = {
    val writer = new PrintWriter(solutionFile)
    for (line <- solution) writer.println(line)
    writer.close()
  }
}
