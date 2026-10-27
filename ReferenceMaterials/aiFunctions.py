import copy
import boardFunctions
import time
import random
import math
import pygame


import json
# Opening JSON file
with open('parameters.json') as f:
    # Load JSON data
    data = json.load(f)

# Access values from the dictionary
emptySpace = data['emptySpace']
n_columns = int(data['n_columns'])
n_rows = int(data['n_rows'])

pygame.init()

def sigmoid(x):
    return 1 / (1 + math.exp(-x))

def AIselect(board, maxdepth = 4, team = 'O'):
    newBoard = copy.deepcopy(board)
    baseNode = AiPlayNode(maxdepth, False, -1, newBoard, 'O')
    winner, selection = baseNode.checkForPossibleWin()

    sorted_nodes = sorted(baseNode.futureNodes, key=lambda node: node.score)

    if winner == 'O': # I have a clear path to victory! the puny human is no match for my intelegence!
        return selection
    elif (len(baseNode.possiblePlays)>0): # anyone's game
        selection = random.choice(baseNode.possiblePlays)
        if sorted_nodes[0].play in baseNode.possiblePlays:
            selection = sorted_nodes[0].play

    else: # the human has bested me, maybe I should send a T-800 back in time so this never happens...
        losingButLegalPlays = []
        for selection in range(n_columns):
            if board[0][selection] == emptySpace:
                losingButLegalPlays.append(selection)
        selection = random.choice(losingButLegalPlays)
    return selection

def AIplay(board, maxdepth = 4, team = 'O'):
  if maxdepth == 3:
    pygame.mixer.music.load('think.mp3')
    pygame.mixer.music.play()
    time.sleep(3)
  elif maxdepth == 4:
    pygame.mixer.music.load('think.mp3')
    pygame.mixer.music.play()
    time.sleep(2)
  elif maxdepth > 4:
    pygame.mixer.music.load('think.mp3')
    pygame.mixer.music.play()
  team = 'O'
  selection = AIselect(board, maxdepth)
  hitPiece = False
  win = False
  pygame.mixer.music.stop()
  for row in range(len(board)):
    if board[row][selection] != emptySpace:
      board[row-1][selection] = team
      if boardFunctions.checkWin(board, row - 1, selection, team) >= 4:
        win = True
      hitPiece = True
      if row == 1:
        for i in range(5):
          board[row-1][selection] = emptySpace
          boardFunctions.clear_output()
          boardFunctions.printBoard(board)
          time.sleep(0.1)
          board[row-1][selection] = team
          boardFunctions.clear_output()
          boardFunctions.printBoard(board)
          time.sleep(0.1)
      break
    else:
      board[row][selection] = team
      boardFunctions.clear_output()
      boardFunctions.printBoard(board)
      pygame.mixer.music.load('fall.mp3')
      pygame.mixer.music.play()
      time.sleep(0.3)
      board[row][selection] = emptySpace
  if not hitPiece:
    board[-1][selection] = team
    if boardFunctions.checkWin(board, len(board)-1, selection, team) >= 4:
      win = True
  pygame.mixer.music.load('place.mp3')
  pygame.mixer.music.play()
  return board, win


class AiPlayNode():
  def __init__(self, maxdepth, previousNode, play, board, team, score = 0):
    self.maxdepth = maxdepth
    self.previousNode = previousNode # the previous node
    self.futureNodes = [] # the nodes coming later
    self.play = play # where you played
    self.board = board
    self.team = team
    self.score = score
    self.possiblePlays = []
    # figure out the possible plays
    for selection in range(n_columns):
        if self.board[0][selection] == emptySpace:
          self.possiblePlays.append(selection)

  def checkForPossibleWin(self):
    # function centered arround sure wins
    # the next team that will play after this node
    if self.team == 'X':
      nextPlayTeam = 'O'
    else:
      nextPlayTeam = 'X'

    totalConnectionScore = 0
    ConnectionWeight = 0.1
    totalEnemyNextNodeScore = 0
    enemyScoreWeight = 0.001
    loosingPlays = []
    if self.maxdepth >= 0: # if we have more processing power continue searching for wins
        for selection in self.possiblePlays: 
            newBoard, connected = simulatePlay(self.board, selection, self.team) 
            # check if AI wins
            if connected >= 4:
                return self.team, selection
            totalConnectionScore += connected 
            newNode = AiPlayNode(self.maxdepth - 1, self, selection, newBoard, nextPlayTeam, connected)
            self.futureNodes.append(newNode)
            winningTeam, s = newNode.checkForPossibleWin()
            totalEnemyNextNodeScore = newNode.score
            if winningTeam == self.team:
                return self.team, selection # we will win later with this play
            elif winningTeam == nextPlayTeam:
                loosingPlays.append(selection) # we will lose with this play

    # do not play anything that allows for a loss
    for loss in loosingPlays:
        self.possiblePlays.remove(loss)
    totalConnectionScore *= ConnectionWeight
    totalEnemyNextNodeScore *= enemyScoreWeight
    self.score = sigmoid(totalConnectionScore-totalEnemyNextNodeScore)
    if (len(self.possiblePlays) > 0): # we can play without loosing

        return emptySpace, -2
    else:
        return nextPlayTeam, -1 # this play is a sure loss





def simulatePlay(board, selection, team):
  newBoard = copy.deepcopy(board)
  hitPiece = False
  connected = 0
  for row in range(len(newBoard)):
    if newBoard[row][selection] != emptySpace:
      newBoard[row-1][selection] = team
      connected = max(connected, boardFunctions.checkWin(newBoard, row - 1, selection, team))
      hitPiece = True
      break
  if not hitPiece:
    newBoard[-1][selection] = team
    connected = max(connected, boardFunctions.checkWin(newBoard, len(newBoard)-1, selection, team))
  return newBoard, connected

  