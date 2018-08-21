Coding exercise
===============

In this step of our process, we want you to provide a solution for one of the problems described below.

## Problem 1 - Robots vs Dinosaurs

Nubank is assembling an army of remote-controlled robots to fight the dinosaurs and the first step towards that is to run simulations on how they will perform. You are tasked with implementing a service that provides an API to support those simulations.

These are the features required:
- Be able to create an empty simulation space - an empty 50 x 50 grid;
- Be able to create a robot in a certain position and facing direction;
- Be able to create a dinosaur in a certain position;
- Issue instructions to a robot - a robot can turn left, turn right, move forward, move backwards, and attack;
- A robot attack destroys dinosaurs around it (in front, to the left, to the right or behind);
- No need to worry about the dinosaurs - dinosaurs don't move;
- Display the simulation's current state;
- A dinosaur and a robot cannot occupy the same position;
- Attempting to move a robot outside the simulation space is an invalid operation.

## Problem 2 - Social media connections

You are tasked with making Nukr, a new social media product by Nu Everything S/A. The initial step is to create a prototype service that provides an API where we can simulate connections between people, and explore how we would offer new connection suggestions.

These are the features required:
- Be able to add a new profile;
- Be able to tag two profiles as connected;
- Be able to generate a list of new connection suggestions for a certain profile, taking the stance that the more connections a profile has with another profile's connections, the better ranked the suggestion should be;
- Some profiles can, for privacy reasons, opt to be hidden from the connection suggestions.

--

# Requirements
- You must pick *one*, and *only one*, of the problems above and implement a solution;
- It *must* be written in a functional programming language (Clojure, Scala, Haskell or Elixir);
- You don't have to worry about databases; it's fine to use in-process, in-memory storage;
- It must be production quality according to your understanding of it – tests, logs, build file, README, etc.

# General notes
- This challenge may be extended by you and a Nubank engineer on a different step of the process;
- Please make sure to anonymise your submission by removing your name from file headers, package names, and such;
- Feel free to expand your design in writing;
- You will submit the source code for your solution to us as a compressed file containing all the code and possible documentation. Please make sure to not include unnecessary files such as the Git repository, compiled binaries, etc;
- Please do not upload your solution to public repositories in GitHub, BitBucket, etc.
- Don't shy away from asking questions whenever you encounter a problem.

# Things we are looking for
- Immutability;
- Idiomatic code;
- Adherence to community/standard library style guides;
- Separation of concerns;
- Unit and integration tests;
- API design;
- Domain modelling;
- Error handling.