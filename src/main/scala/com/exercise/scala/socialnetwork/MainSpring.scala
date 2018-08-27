package com.exercise.scala.socialnetwork

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{ComponentScan, Configuration}

@Configuration
@EnableAutoConfiguration
@ComponentScan
class Config

/**
  * <h1> Main </h1>
  *
  * The Main class of Spring Application (Nukr - Social Network).
  *
  * @version 0.0.1
  */
object MainSpring extends App {
  SpringApplication.run(classOf[Config])
}