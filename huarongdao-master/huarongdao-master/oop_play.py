#不同类别的任务要分开#
class Piece:
    def __init__(self, height, width, x, y, name):
        self.width = width
        self.height = height
        self.name = name
        self.x = x
        self.y = y
    def check_k(self, direction, position1, position2):
        k = [position1, position2]
        ok = []
        for i in k:
            if direction == 0:
                ok.append(i)
            if direction == 1:
                if (i[0] >= self.x and i[0] <= self.x + self.height - 1) and i[1] == self.y - 1:
                    ok.append(i)
            elif direction == 2:
                if (i[0] >= self.x and i[0] <= self.x + self.height - 1) and i[1] == self.y + self.width:
                    ok.append(i)
            elif direction == 3:
                if (i[1] >= self.y and i[1] <= self.y + self.width - 1) and i[0] == self.x - 1:
                    ok.append(i)
            else:
                if (i[1] >= self.y and i[1] <= self.y + self.width - 1) and i[0] == self.x + self.height:
                    ok.append(i)
        return ok
    def check_move(self, direction, position1, position2):
        ok = self.check_k(direction, position1, position2)
        if direction <= 2 and len(ok) >= self.height:
            return True
        elif direction > 2 and len(ok) >= self.width:
            return True
        return False
    def can_move(self, position1, position2):
        for i in range(1, 5):
            if self.check_move(i, position1, position2):
                return True
        return False


class State:
    def __init__(self, positions, dic):
        self.positions = positions
        self.dic = dic
        self.piece = [Piece(h, w, x, y, n) for (x, y), (h, w, n) in zip(positions, dic)]
        self.k1 = self.piece[-1]
        self.k2 = self.piece[-2]
    def check_num(self, move_num):
        piece_n = self.piece[move_num]
        if piece_n.can_move((self.k1.x, self.k1.y), (self.k2.x, self.k2.y)):
            return True
        else:
            return False
    def check_move(self, move_num, direction):#检查整体移动#
        piece_n = self.piece[move_num]
        if piece_n.check_move(direction, (self.k1.x, self.k1.y), (self.k2.x, self.k2.y)):
            return True
        else:
            return False


        

class Show:
    def __init__(self):
        pass
    def print_out(self, state):
        diction = [(x, y, h, w, str(idx), name) for idx, ((x, y),  (h, w, name)) in enumerate(zip(state.positions, state.dic))]
        #25行逐一print#
        for i in range(25):
            chosen = list(filter(lambda x: x[0] <= i / 5 and x[0] + x[2] > i / 5, diction))
            chosen = sorted(chosen, key = lambda x: x[1])
            #找出每一行涉及方块并按顺序排列#
            line = ""
            #每一个方块在这一行的情况#
            for x in chosen:
                if x[5] == "k":#空格#
                    line += " " * 9
                elif i == x[0] * 5:#上下边界#
                    if x[3] == 1:
                        line += "┌" + "━" * 7 + "┐"
                    else:
                        line += "┌" + "━" * 16 + "┐"
                elif i == (x[0] + x[2]) * 5 - 1:
                    if x[3] == 1:
                        line += "└" + "━" * 7 + "┘"
                    else:
                        line += "└" + "━" * 16 + "┘"
                elif x[2] == 1 and x[3] == 2 and i == x[0] * 5 + 2:#横将#
                    line += "│" + " " * 5 + x[4] + " " + x[5] + " " * 5 + "│"
                elif x[2] == 1 and x[3] == 1 and i == x[0] * 5 + 2:#兵#
                    line += "│" + " " * 1 + x[4] + " " + x[5] + " " * 2 + "│"
                elif x[2] == 2 and x[3] == 2 and i == x[0] * 5 + 4:#曹操数字#
                    line += "│" + " " * 8 + x[4] + " " * 7 + "│"
                elif x[2] == 2 and x[3] == 1 and i == x[0] * 5 + 4:#竖将数字#
                    line += "│" + " " * 3 + x[4] + " " * 3 + "│"
                elif x[2] == 2 and x[3] == 2 and i == x[0] * 5 + 5:#曹操#
                    line += "│" + " " * 6 + x[5] + " " * 6 + "│"
                elif x[2] == 2 and x[3] == 1 and i == x[0] * 5 + 5:#竖将#
                    line += "│" + " " * 1 + x[5] + " " * 2 + "│"
                else:#方格内空白#
                    if x[3] == 1:
                        line += "│" + " " * 7 + "│"
                    else:
                        line += "│" + " " * 16 + "│"
            print(line)
        print(" " * 8 + "│" + " " * 3 + "曹操目标位置" + " " * 3 + "│" + " " * 8)
    def ask_level(self, levels):
        print(1)
        for i in range(len(levels)):
            print(f"{i} {levels[i]}")
        n = input("请选择关卡编号：")#筛选#
        return int(n)


class Game:
    def __init__(self, positions, dic):
        self.levels = ["横刀立马", "小试牛刀"]
        self.positions = positions
        self.dic = dic
    def move(self, move_num, direction):
        state_now = State(self.positions, self.dic)
        move_piece = state_now.piece[move_num]
        if direction == 1:
            move_piece.y -= 1
        elif direction == 2:
            move_piece.y += 1
        elif direction == 3:
            move_piece.x -= 1
        elif direction == 4:
            move_piece.x += 1
    def check_move_steps(self, move_num, direction1, direction2):
        state_now = State(self.positions, self.dic)
        positions1 = self.positions.copy()
        if state_now.check_move(move_num, direction1):
            self.move(move_num, direction1)
            if state_now.check_move(move_num, direction2):
                self.positions = positions1
                return True
        self.positions = positions1
        return False
    def move_steps(self, move_num, direction1, direction2):
        move_dire = (direction1, direction2)
        for i in move_dire:
            self.move(move_num, i)
    def update(self, move_num, direction):
        state_now = State(self.positions, self.dic)
        piece_n = state_now.piece[move_num]
        k1 = state_now.piece[-1]
        k2 = state_now.piece[-2]
        ok = piece_n.check_k(direction, (k1.x, k1.y), (k2.x, k2.y))


state0 = [(0, 2), (3, 2), (3, 3), (4, 2), (4, 3), (2, 2), (1, 0), (1, 1), (0, 0), (3, 0), (1, 2), (1, 3)]
dic0 = [(1, 2, "关羽"), (1, 1, "兵"), (1, 1, "兵"), (1, 1, "兵"), (1, 1, "兵"),
           (1, 2, "黄忠"), (2, 1, "马超"), (2, 1, "赵云"), (1, 2, "张飞"), (2, 2, "曹操"),
           (1, 1, "k"), (1, 1, "k")]
state1 = [(0, 0), (4, 0), (4, 1), (4, 2), (4, 3), (0, 3), (2, 0), (0, 1), (2, 1), (2, 2), (0, 2), (1, 2)]
dic1 = [(2, 1, "关羽"), (1, 1, "兵"), (1, 1, "兵"), (1, 1, "兵"), (1, 1, "兵"),
           (2, 1, "黄忠"), (2, 1, "马超"), (2, 1, "赵云"), (2, 1, "张飞"), (2, 2, "曹操"),
           (1, 1, "k"), (1, 1, "k")]
state2 = [(0, 1), (0, 0), (0, 3), (3, 1), (3, 2), (1, 3), (3, 0), (1, 0), (3, 3), (1, 1), (4, 1), (4, 2)]
dic2 = [(1, 2, "关羽"), (1, 1, "兵"), (1, 1, "兵"), (1, 1, "兵"), (1, 1, "兵"),
           (2, 1, "黄忠"), (2, 1, "马超"), (2, 1, "赵云"), (2, 1, "张飞"), (2, 2, "曹操"),
           (1, 1, "k"), (1, 1, "k")]
state3 = state = [(2, 1), (3, 1), (3, 2), (4, 0), (4, 3), (0, 0), (0, 3), (2, 0), (2, 3), (0, 1), (4, 1), (4, 2)]
dic3 = [(1, 2, "关羽"), (1, 1, "兵"), (1, 1, "兵"), (1, 1, "兵"), (1, 1, "兵"),
           (2, 1, "黄忠"), (2, 1, "马超"), (2, 1, "赵云"), (2, 1, "张飞"), (2, 2, "曹操"),
           (1, 1, "k"), (1, 1, "k")]
levels = [("0小试牛刀", state0, dic0), ("1七步成诗", state1, dic1), ("2海阔天空", state2, dic2), ("3横刀立马", state3, dic3)]

display = Show()

level = display.ask_level([x[0] for x in levels])


n, p, d = levels[level]

state = State(p, d)
display.print_out(state)

# game = Game(p, d)
