package com.exercise.scala.socialnetwork

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{ComponentScan, Configuration}

@Configuration
@EnableAutoConfiguration
@ComponentScan
class Config

object Main extends App {
  SpringApplication.run(classOf[Config])
}
