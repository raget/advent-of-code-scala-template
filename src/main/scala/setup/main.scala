package setup

import day0.PuzzleFiles

import java.io.*
import java.nio.file.Paths

@main
def main(): Unit =
  print("Enter the day number: ")
  val day = scala.io.StdIn.readLine()
  val puzzleFiles = new PuzzleFiles(day)
  println()

  println("Generating input files for day " + day)
  puzzleFiles.createInputFiles.foreach(println)
  println()

  println("Generating sources for day " + day)
  println(puzzleFiles.createSources)
  println()

  println("Generating tests for day " + day)
  println(puzzleFiles.createTests)
  println()


