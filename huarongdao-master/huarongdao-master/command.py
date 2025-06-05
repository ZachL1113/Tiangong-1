class Command:
    def __init__(self, isMove, what, where = None):
        self.isMove = isMove
        self.what = what#移动哪一个or哪个特殊指令#
        self.where = where#若是move指令，则需输入朝哪里走#
        self.isBack = what == "Back"#判断是不是这些特殊指令#
        self.isReset = what == "Restart"
        self.isExit = what == "Exit"
        self.isChose = what == "Chose"

    def moveCommand(piece, position):#静态指令，不由某一个该类例子调用，直接由类名调用#
        if len(piece) != 1 or not '0' <= piece <= '9':
            raise Exception("移动指令的棋子编号参数不正确")
        return Command(True, int(piece), position.upper())#直接处理为大写##要添加完备性筛选#
    #特殊指令们#
    def resetCommand():
        return Command(False, "Restart")

    def backCommand():
        return Command(False, "Back")

    def exitCommand():
        return Command(False, "Exit")

    def choseCommand():
        return Command(False, "Chose")

    def pieceNum(self):
        if not self.isMove:
            raise Exception("不是移动指令，不可获得棋子编号")
        return self.what

    def isSingleMove(self):
        if not self.isMove:
            raise Exception("不是移动指令，不可获得棋子编号")
        return len(self.where) == 1

    def isMutilMove(self):
        if not self.isMove:
            raise Exception("不是移动指令，不可获得棋子编号")
        return len(self.where) == 2

    def isDoubleMove(self):
        return self.isMutilMove() and self.where[0] == self.where[1]

    def isUpFrist(self):
        return self.where[0] == 'U'

    def isDownFrist(self):
        return self.where[0] == 'D'

    def isRightFirst(self):
        return self.where[0] == 'R'

    def isLeftFirst(self):
        return self.where[0] == 'L'

    def isThenUp(self):
        return self.where[1] == 'U'

    def isThenDown(self):
        return self.where[1] == 'D'

    def isThenRight(self):
        return self.where[1] == 'R'

    def isThenLeft(self):
        return self.where[1] == 'L'

    def __str__(self):
        return f"{self.isMove} {self.what} {self.where}"