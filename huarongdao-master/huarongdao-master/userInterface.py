from play0 import print_out#改成oop#
from command import Command

commands = {"Q": Command.exitCommand(), "C": Command.choseCommand(), "R": Command.resetCommand(), "B": Command.backCommand()}
CELL_WIDTH = 9
class ShowPiece:
    def __init__(self, piece):
      self.h = piece.h
      self.w = piece.w
      self.x = piece.x
      self.y = piece.y
      self.name = piece.name
      self.num = piece.num

    def createRow(self, row_num):
        w = CELL_WIDTH * self.w
        h = self.h * 5
        if self.name >= 10:#空格#
            return " " * w
        elif row_num == 0:#上底#
            return "┌" + "━" * (w - 2) + "┐"
        elif row_num == h - 1:#下底#
            return "└" + "━" * (w - 2) + "┘"
        elif self.h == 1 and row_num == 3:#兵、横将#
            name_size = len(self.name) * 2 + len(self.num) + 3
            return "│" + " " * (w - name_size - 2) // 2 + self.num + " " + self.name + " " * ((w - 7) // 2 + w % 2) + "│"
        elif self.h == 2 and row_num == 5:#曹操、竖将数字#
            return "│" + " " * ((w - 3) // 2 + (w - 3) % 2) + self.num + " " * (w - 3) // 2 + "│"
        elif self.h == 2 and row_num == 6:#曹操、竖将名字#
            return "│" + " " * (w - len(self.name) * 2 - 2) // 2 + self.name + " " * ((w - 6) // 2 + w % 2) + "│"
        else:
            return "│" + " " * (w - 2) + "│"
    def getRow(self, row_num):
        if self.y <= row_num / 5 and self.y + self.h > row_num / 5:
           return self.createRow(row_num - self.y * 5)
        else:
           return ""


class UserInterface:

  def print_out(self, state):
    p = [ShowPiece(i) for i in state.pieces]
    p = sorted(p, key = lambda x: x.x)
    for i in range(25):
       print("".join([k.getRow(i) for k in p]))
    print(" " * 8 + "│" + " " * 3 + "曹操目标位置" + " " * 3 + "│" + " " * 8)


  def show(self, state):
    s = []
    d = []
    for piece in state.pieces:
      s.append((piece.y, piece.x))
      d.append((piece.h, piece.w, piece.name))
    print_out(s, d)

  def getInputs(self):
    return input()

  def showResult(self, result = True):
    if result:
      print("恭喜你，通关成功！")
    else:
      print("很遗憾，通关失败！")

  def askLevel(self, levels):
    print("请选择关卡：")
    for i in range(len(levels)):
      print(f"{i}: {levels[i][0]}")
    while True:
      level_chosen = input("输入编号：（如想退出，请输入Q）").upper()
      if '0' <= level_chosen <= f'{len(levels) - 1}' or level_chosen == 'Q':
         return level_chosen

  def askCommand(self, history):
    print(f"当前已移动：{len(history) - 1}步")
    while True:
      next_num = input("请输入您想移动的棋子编号：（如想退出，请输入Q；如想回到上一步，请输入B；如想重置，请输入R；如想回到选关界面，请输入C）").upper()
      if len(next_num) == 1:
        if '0' <= next_num <= '9':
          while True:
            print("L（左），R（右），U（上），D（下），多次移动请连续输入，例如：RR（右右），DL（下左），可不区分大小写")
            next_move = input("请输入您想如何移动它：").upper()
            if 1 <= len(next_move) <= 2:
              inputing = False
              for i in range(len(next_move)):
                if next_move[i] not in ["L", "R", "U", "D"]:
                  inputing = True
              if not inputing:
                command = Command.moveCommand(next_num, next_move)
                state = history[-1]
                if state.buildMove(command).isValid():
                  return command
        elif next_num in commands:
          return commands[next_num]
