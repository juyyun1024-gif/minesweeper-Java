# Minesweeper — Java Implementation

University of Minnesota – Advanced Programming Principles
Author: Justin Yun

## Overview

This project is a Java implementation of the classic Minesweeper game. The objective is to uncover all safe tiles on the board without triggering any mines. The program demonstrates object-oriented programming principles and emphasizes encapsulation, modular design, and game logic management.

## Theory

The game operates on a grid-based structure composed of cells. Each cell can contain a mine or a number indicating the count of adjacent mines.
Using Java’s object-oriented capabilities, the program organizes the logic into classes that represent the board, cells, and gameplay flow.

Compilation and execution are performed using the standard Java compiler and runtime environment.

## Example:

javac Main.java

java Main

## Implementation Structure

Board – Manages grid creation, mine placement, and adjacency calculations.

Cell – Represents each square in the grid, storing whether it’s revealed, marked, or mined.

Game – Handles user input, move validation, and win/loss conditions.

Main – Initializes the game and runs the main loop or GUI interface.

The program can be implemented as either a console-based or graphical version depending on design choices.

## Usage

When the game starts, a grid of covered cells is displayed.
Players can perform actions such as:

Reveal a cell: uncover the chosen location.

Mark a cell: flag a suspected mine.

Continue until all safe cells are revealed or a mine is hit.

## Example console session:

Welcome to Minesweeper!

Enter command:

r 1 1

Safe! 0 mines nearby.

m 2 2

Marked as mine.

r 3 3

Boom! You hit a mine. Game over.
