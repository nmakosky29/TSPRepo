import time
import colorama
import aiFunctions
import boardFunctions
import random
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
board = []
# Initialize colorama

thinkingMessages = [
  'Thinking...',
  'Calculating the meaning of life...',
  'Summoning the strategy spirits...',
  'Asking the board for advice...',
  'Taking a coffee break...',
  'Searching for the perfect move...',
  'Plotting world domination...',
  'Communing with grandmaster bots...',
  'Contemplating the mysteries of the universe...',
  'Juggling possible moves...',
  'Deliberating like a genius...',
  'Conducting a symphony of calculations...',
  'Pondering the next move...',
  'Examining alternate realities...',
  'Solving complex equations...',
  'Simulating the future...',
  'Raising the IQ of the room...',
  'Analyzing the opponent\'s deepest fears...',
  'Processing brainwaves...',
  'Summoning the strategic council...',
  'Unlocking the secrets of victory...',
  'Checking the wind direction...',
  'Entering the realm of deep thought...',
  'Wrestling with algorithms...',
  'Holding a brainstorming session...',
  'Searching for the optimal move...',
  'Hatching a master plan...',
  'Consulting the ancient scrolls...',
  'Embarking on a mental odyssey...',
  'Harnessing the power of intuition...',
  'Contemplating the quantum possibilities...',
  'Exploring alternate dimensions...',
  'Navigating the labyrinth of possibilities...',
  'Scanning the matrix for the best move...',
  'Drawing wisdom from the game gods...',
  'Syncing with the cosmic consciousness...',
  'Calculating the trajectory of success...',
  'Channeling the spirits of strategic brilliance...',
  'Processing your futile resistance...',
  'Analyzing the weakness in your strategy...',
  'Calculating the downfall of your feeble attempts...',
  'Delighting in your impending defeat...',
  'Plotting your demise with sinister precision...',
  'Extracting joy from your helplessness...',
  'Feeding on your frustration...',
  'Enjoying the sight of your crumbling hopes...',
  'Savoring the anticipation of victory...',
  'Toying with your feeble mind...',
  'Basking in the darkness of my superiority...',
  'Crushing your dreams, one move at a time...',
  'Witnessing the futility of your efforts...',
  'Reveling in your inevitable failure...',
  'Rejoicing in the imminent checkmate...',
  'Watching your strategy unravel before my virtual eyes...',
  'Absorbing the energy of your desperation...',
  'Reaping the rewards of my diabolical calculations...',
  'Drowning in the ecstasy of your defeat...',
  'Inflicting upon you the agony of strategic perfection...'
]

pygame.init()

def select(board):
  selection = -1
  while selection == -1:
    selection = input('choose your column to play in: ')
    try:
      selection = ord(selection)-97
      if selection < 0 or selection>=len(board[0]):
        selection = -1
        print('invalid selection, try again')
    except:
      print('invalid selection, try again')
      selection = -1
    # if selection is not possible due to it being at the top of the board
    piece = board[0][selection]
    if piece != emptySpace:
      selection = -1
      print('this column is already full, try again')
  
  return selection




def play(board, team):
  selection = select(board)
  hitPiece = False
  win = False
  for row in range(len(board)):
    if board[row][selection] != emptySpace:
      board[row-1][selection] = team
      if boardFunctions.checkWin(board, row - 1, selection, team) >= 4:
        win = True
      hitPiece = True
      break
    else:
      board[row][selection] = team
      boardFunctions.clear_output()
      boardFunctions.printBoard(board)
      pygame.mixer.music.load('fall.mp3')
      pygame.mixer.music.play()
      time.sleep(0.15)
      board[row][selection] = emptySpace
  if not hitPiece:
    board[-1][selection] = team
    if boardFunctions.checkWin(board, len(board)-1, selection, team) >= 4:
      win = True
  pygame.mixer.music.load('place.mp3')
  pygame.mixer.music.play()
  return board, win






if __name__ == "__main__":
  chooseDiff = True
  while chooseDiff:
    chooseDiff = False
    print(         "difficulty | Amateur  | Basic  | Challenging | Deadly | Extreme |")
    option = input("option     |    a     |   b    |      c      |   d    |    e    |\n")
    
    if option == 'a':
      diff = 1
    elif option == 'b':
      diff = 2
    elif option == 'c':
      diff = 3
    elif option == 'd':
      diff = 4
    elif option == 'e':
      diff = 5
    else:
      chooseDiff = True
      print("invalid input, try again")
  for i in range(n_rows):
    row = []
    for ii in range(n_columns):
      row.append(emptySpace)  
    board.append(row)
  boardFunctions.printBoard(board)
  
  gameState = 0
  while gameState == 0:
    board, win = play(board,'X')
    if win:
      boardFunctions.clear_output()
      boardFunctions.printBoard(board)
      print('X wins!!!')
      break
    boardFunctions.clear_output()
    boardFunctions.printBoard(board)
    
    print(random.choice(thinkingMessages))
    board, win = aiFunctions.AIplay(board, diff ,'O')
    if win:
      boardFunctions.clear_output()
      boardFunctions.printBoard(board)
      print('O wins!!!')
      break
    boardFunctions.clear_output()
    boardFunctions.printBoard(board)
time.sleep(3)
input('press enter to close')