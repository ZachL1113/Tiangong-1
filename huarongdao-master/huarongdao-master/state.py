state0 = [
    (0, 2),
    (3, 2),
    (3, 3),
    (4, 2),
    (4, 3),
    (2, 2),
    (1, 0),
    (1, 1),
    (0, 0),
    (3, 0),
    (1, 2),
    (1, 3),
]
dic0 = [
    (1, 2, "关羽"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (1, 2, "黄忠"),
    (2, 1, "马超"),
    (2, 1, "赵云"),
    (1, 2, "张飞"),
    (2, 2, "曹操"),
    (1, 1, "k"),
    (1, 1, "k"),
]
state1 = [
    (0, 0),
    (4, 0),
    (4, 1),
    (4, 2),
    (4, 3),
    (0, 3),
    (2, 0),
    (0, 1),
    (2, 1),
    (2, 2),
    (0, 2),
    (1, 2),
]
dic1 = [
    (2, 1, "关羽"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (2, 1, "黄忠"),
    (2, 1, "马超"),
    (2, 1, "赵云"),
    (2, 1, "张飞"),
    (2, 2, "曹操"),
    (1, 1, "k"),
    (1, 1, "k"),
]
state2 = [
    (0, 1),
    (0, 0),
    (0, 3),
    (3, 1),
    (3, 2),
    (1, 3),
    (3, 0),
    (1, 0),
    (3, 3),
    (1, 1),
    (4, 1),
    (4, 2),
]
dic2 = [
    (1, 2, "关羽"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (2, 1, "黄忠"),
    (2, 1, "马超"),
    (2, 1, "赵云"),
    (2, 1, "张飞"),
    (2, 2, "曹操"),
    (1, 1, "k"),
    (1, 1, "k"),
]
state3 = state = [
    (2, 1),
    (3, 1),
    (3, 2),
    (4, 0),
    (4, 3),
    (0, 0),
    (0, 3),
    (2, 0),
    (2, 3),
    (0, 1),
    (4, 1),
    (4, 2),
]
dic3 = [
    (1, 2, "关羽"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (1, 1, "兵"),
    (2, 1, "黄忠"),
    (2, 1, "马超"),
    (2, 1, "赵云"),
    (2, 1, "张飞"),
    (2, 2, "曹操"),
    (1, 1, "k"),
    (1, 1, "k"),
]
levels = [
    ("0小试牛刀", state0, dic0),
    ("1七步成诗", state1, dic1),
    ("2海阔天空", state2, dic2),
    ("3横刀立马", state3, dic3),
]

from piece import Piece
from space import SpaceAnalyzer
from command import Command
from move import Move

class State:
    def __init__(self, character):
        name, postions, dics = character
        self.dics = dics
        self.pieces = [Piece(postions[num], dics[num], num) for num in range(12)]
        self.name = name

    # 生成指定编号关卡的初始局面
    @staticmethod
    def stateOfLevel(idx):
        return State(levels[idx])

    # 通过提供的用户界面输出当前状态
    def show(self, userInterface):
        userInterface.show(self)

    # 检查指定编号的棋子是否可以移动
    def isPieceCanBePick(self, num):
        p = self.pieces[num]
        sa = SpaceAnalyzer(self.pieces[10:])
        return p.canDown(sa) or p.canUp(sa) or p.canLeft(sa) or p.canRight(sa)

    # 基于用户输入的移动指令创建Move对象
    def buildMove(self, moveCommand):
        p = self.pieces[moveCommand.pieceNum()]
        sar = SpaceAnalyzer(self.pieces[10:])
        sa = sar.spaceA
        sb = sar.spaceB
        if moveCommand.isDoubleMove():
          if moveCommand.isUpFrist() and sb.isOnOf(p) and sar.isHighClosed and not p.isWideGeneral():
              # 非宽体上双移
              if p.isHighGeneral():
                  return Move.changeAB(p, sa).with_A2B(sb, (sb.x, sb.y + p.h))
              return Move.changeAB(p, sa)
          if moveCommand.isDownFrist() and sa.isUnderOf(p) and sar.isHighClosed and not p.isWideGeneral():
              # 非宽体下双移()
              if p.isHighGeneral():
                  return Move.changeAB(p, sa).with_A2B(sb, (sb.x, sb.y - p.h))
              return Move.changeAB(p, sb)
          if moveCommand.isLeftFirst() and sb.isLeftOf(p) and sar.isWideClosed and not p.isHighGeneral():
              # 非高个左双移
              if p.isWideGeneral():
                  return Move.changeAB(p, sa).with_A2B(sb, (sb.x + p.w, sb.y))
              return Move.changeAB(p, sa)
          if moveCommand.isRightFirst() and sb.isRightOf(p) and sar.isWideClosed and not p.isHighGeneral():
              # 非高个右双移
              if p.isWideGeneral():
                  return Move.changeAB(p, sa).with_A2B(sb, (sb.x - p.w, sb.y))
              return Move.changeAB(p, sb)
        elif moveCommand.isMutilMove() and p.isSoldier():
          # 单兵上拐左右
          if moveCommand.isUpFrist() and sa.isLeftOf(sb):
              if moveCommand.isThenLeft() and sb.isOnOf(p):
                  return Move.changeAB(p, sa)
              if moveCommand.isThenRight() and sa.isOnOf(p):
                  return Move.changeAB(p, sb)
          # 单兵下拐左右
          if moveCommand.isDownFrist() and sa.isLeftOf(sb):
              if moveCommand.isThenLeft() and sb.isUnderOf(p):
                  return Move.changeAB(p, sa)
              if moveCommand.isThenRight() and sa.isUnderOf(p):
                  return Move.changeAB(p, sb)
          # 单兵左拐上下
          if moveCommand.isLeftFirst() and sb.isUnderOf(sa):
              if moveCommand.isThenUp() and sb.isLeftOf(p):
                  return Move.changeAB(p, sa)
              if moveCommand.isThenDown() and sa.isLeftOf(p):
                  return Move.changeAB(p, sb)
          # 单兵右拐上下
          if moveCommand.isRightFirst() and sb.isUnderOf(sa):
              if moveCommand.isThenUp() and sb.isRightOf(p):
                  return Move.changeAB(p, sa)
              if moveCommand.isThenDown() and sa.isRightOf(p):
                  return Move.changeAB(p, sb)
        elif moveCommand.isSingleMove():
          if moveCommand.isUpFrist() and p.canUp(sar):
              if p.isWideGeneral():
                  return Move.a2b_b2c_d2e(p, sa, (sa.x, sa.y + p.h), sb, (sb.x, sb.y + p.h))
              else:
                  s = sa if p.isUnderOf(sa) else sb
                  return Move.a2b_b2c(p, s, (s.x, s.y + p.h))
          if moveCommand.isDownFrist() and p.canDown(sar):
              if p.isWideGeneral():
                  return Move.a2b_b2c_d2e(sa, p, (p.x, p.y + 1), sb, (sb.x, sb.y - p.h))
              else:
                  s = sa if p.isOnOf(sa) else sb
                  return Move.a2b_b2c(s, p, (p.x, p.y + 1))
          if moveCommand.isLeftFirst() and p.canLeft(sar):
              if p.isHighGeneral():
                  return Move.a2b_b2c_d2e(p, sa, (sa.x + p.w, sa.y), sb, (sb.x + p.w, sb.y))
              else:
                  s = sa if p.isRightOf(sa) else sb
                  return Move.a2b_b2c(p, s, (s.x + p.w, s.y))
          if moveCommand.isRightFirst() and p.canRight(sar):
              if p.isHighGeneral():
                  return Move.a2b_b2c_d2e(sa, p, (p.x + 1, p.y), sb, (sb.x - p.w, sb.y))
              else:
                  s = sa if p.isLeftOf(sa) else sb
                  return Move.a2b_b2c(s, p, (p.x + 1, p.y))
        return Move()
    
    def clone(self):
        return State((self.name, [(p.y, p.x) for p in self.pieces], self.dics))
    
    #检查当前状态是否已通关
    def check_success(self):
        return self.pieces[9].x == 1 and self.pieces[9].y == 3