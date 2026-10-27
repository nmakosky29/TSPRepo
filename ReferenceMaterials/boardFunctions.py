import os
import colorama
from colorama import Fore, Style

import json
# Opening JSON file
with open('parameters.json') as f:
    # Load JSON data
    data = json.load(f)

# Access values from the dictionary
emptySpace = data['emptySpace']
n_columns = int(data['n_columns'])
n_rows = int(data['n_rows'])

board = []
botRow = ' | '
for letter in range(n_columns):
   botRow+=chr(letter+97)+' | '
# Initialize colorama
colorama.init()


def clear_output():
    # Clear the console output for Windows, Linux, and macOS
    os.system('cls' if os.name == 'nt' else 'clear')

def printBoard(board):
  for row in board:
    print(end = ' | ')
    for char in row:
      if char == 'X':
        print(Fore.BLUE+char+Style.RESET_ALL, end = ' | ')
      else:
        print(Fore.RED+char+Style.RESET_ALL, end = ' | ')
    print()
  print(botRow)


def checkWin(board, row, column, team):
  # vertical win
  vert = 1
  try:
    if board[row-1][column] == team and row-1 >= 0:
      vert += 1
      if board[row-2][column] == team and row-2 >= 0:
        vert += 1
        if board[row-3][column] == team and row-3 >= 0:
          vert += 1
  except:
    pass
  try:
    if board[row+1][column] == team:
      vert += 1
      if board[row+2][column] == team:
        vert += 1
        if board[row+3][column] == team:
          vert += 1        
  except:
    pass

  # horizontal win
  horizontal = 1
  try:
    if board[row][column-1] == team and column-1 >= 0:
      horizontal += 1
      if board[row][column-2] == team and column-2 >= 0:
        horizontal += 1
        if board[row][column-3] == team and column-3 >= 0:
          horizontal += 1
  except:
    pass
  try:
    if board[row][column+1] == team:
      horizontal += 1
      if board[row][column+2] == team:
        horizontal += 1
        if board[row][column+3] == team:
          horizontal += 1        
  except:
    pass
  # diagonal win up left/ down right
  diagonalA = 1
  try:
    if board[row-1][column-1] == team and row-1 >= 0 and column-1 >= 0:
      diagonalA += 1
      if board[row-2][column-2] == team and row-2 >= 0 and column-2 >= 0:
        diagonalA += 1
        if board[row-3][column-3] == team and row-3 >= 0 and column-3 >= 0:
          diagonalA += 1
  except:
    pass
  try:
    if board[row+1][column+1] == team:
      diagonalA += 1
      if board[row+2][column+2] == team:
        diagonalA += 1
        if board[row+3][column+3] == team:
          diagonalA += 1        
  except:
    pass

    # diagonal win down left/ up right
  diagonalB = 1
  try:
    if board[row+1][column-1] == team and column-1 >= 0:
      diagonalB += 1
      if board[row+2][column-2] == team and column-2 >= 0:
        diagonalB += 1
        if board[row+3][column-3] == team and column-3 >= 0:
          diagonalB += 1
  except:
    pass
  try:
    if board[row-1][column+1] == team and row-1 >= 0:
      diagonalB += 1
      if board[row-2][column+2] == team and row-2 >= 0:
        diagonalB += 1
        if board[row-3][column+3] == team and row-3 >= 0:
          diagonalB += 1        
  except:
    pass
  # true if you won
  return max(max(vert, horizontal), max(diagonalA, diagonalB))
#   return vert >= 4 or horizontal >= 4 or diagonalA >= 4 or diagonalB >= 4
