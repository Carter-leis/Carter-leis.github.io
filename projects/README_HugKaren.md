# Hug The Angry Karen

## Overview
  Hug the angry Karen is a game that I created for a class. The user has to go around the board and find the character and capture it. The character would leave traps around the board where it went, causing the user to be stuck for some time. Each block that was moved over the angry Karen would be visible on. Once the user captured the Karen, the game would be over.
  
## Board.java
This file creates the board that the game will be played on. It also moves the user around the board creating visible spaces where the user has gone. It also creates the bombs that the boos has and sets them in places at a certain period of time.

## Boardable.java
This is interface that returns if a cell is visible or not.

## Direction.java
This only contains an enum that stores the possible direction that the user can move.

## Driver.java
This is what you use to start the game by defining how big the user wants to board to be.

## Stylus.java
This file reads what the user types into the key board to make the user move. Each of the keys Q, W, E, A, D, Z, X, and C all have different directional movement. This file will send to the board.java which one has been pressed.

## TestDriver.java
This file was used to test the following files making sure that each of them worked properly.
