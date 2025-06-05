from space import SpaceAnalyzer

class Piece:
    def __init__(self, position, dic, num):
        self.y = position[0]
        self.x = position[1]
        self.h = dic[0]
        self.w = dic[1]
        self.name = dic[2]
        self.num = num

    # 检查棋子是否在指定棋子之上
    def isOnOf(self, other, second = None):
        return self.x == other.x and self.y + self.h == other.y

    # 检查棋子是否在指定棋子之下
    def isUnderOf(self, other):
        return self.x == other.x and self.y == other.y + other.h

    # 检查棋子是否在指定棋子之左
    def isLeftOf(self, other):
        return self.x + self.w == other.x and self.y == other.y

    # 检查棋子是否在指定棋子之右
    def isRightOf(self, other):
        return self.x == other.x + other.w and self.y == other.y

    # 检查两个棋子是否相邻
    def cheekByJowl(self, other):
        return self.isUnderOf(other) or self.isOnOf(other) or self.isLeftOf(other) or self.isRightOf(other)

    # 检查棋子是否是宽棋子
    def isWideGeneral(self):
        return self.w == 2

    # 检查棋子是否是高棋子
    def isHighGeneral(self):
        return self.h == 2

    def isSpace(self):
        return self.name == "k"

    # 检查棋子是否是曹操
    def isKing(self):
        return self.name == "曹操"

    # 检查棋子是否是小兵
    def isSoldier(self):
        return self.h == 1 and self.w == 1

    # 检查棋子是否是将军
    def isGeneral(self):
        return self.h == 2 or self.w == 2

    # 将本棋子移动到指定位置
    def move(self, x, y):
        self.x = x
        self.y = y

    # 检查棋子是否可以向下移动
    def canDown(self, spaceAnalyser):
        if self.isWideGeneral():
            return spaceAnalyser.isWideClosed and self.isOnOf(spaceAnalyser.spaceA)
        return self.isOnOf(spaceAnalyser.spaceA) or self.isOnOf(spaceAnalyser.spaceB)

    # 检查棋子是否可以向上移动
    def canUp(self, spaceAnalyser):
        if self.isWideGeneral():
            return spaceAnalyser.isWideClosed and self.isUnderOf(spaceAnalyser.spaceA)
        return self.isUnderOf(spaceAnalyser.spaceA) or self.isUnderOf(spaceAnalyser.spaceB)

    # 检查棋子是否可以向左移动
    def canLeft(self, spaceAnalyser):
        if self.isHighGeneral():
            return spaceAnalyser.isHighClosed and self.isRightOf(spaceAnalyser.spaceA)
        return self.isRightOf(spaceAnalyser.spaceA) or self.isRightOf(spaceAnalyser.spaceB)

    # 检查棋子是否可以向右移动
    def canRight(self, spaceAnalyser):
        if self.isHighGeneral():
            return spaceAnalyser.isHighClosed and self.isLeftOf(spaceAnalyser.spaceA)
        return self.isLeftOf(spaceAnalyser.spaceA) or self.isLeftOf(spaceAnalyser.spaceB)

    # 复制当前棋子称为一个新的棋子
    def clone(self):
        return Piece((self.x, self.y), (self.h, self.w, self.name), self.num)