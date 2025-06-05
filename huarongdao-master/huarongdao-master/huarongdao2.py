a = "┌" + "━" * 3 + "┐"
b = "┌" + "━" * 8 + "┐"
c = "│" + " " * 6 + "│"
d = "│" + " " * 16 + "│"
e = "└" + "━" * 3 + "┘"
f = "└" + "━" * 8 + "┘"
g = " " * 10
def print_out(state):
    diction = {(state[0][0],state[0][1], 1, 2):"0 关羽", (state[1][0], state[1][1], 1, 1):"1 兵", (state[2][0], state[2][1], 1, 1):"2 兵",
               (state[3][0], state[3][1], 1, 1):"3 兵",(state[4][0], state[4][1], 1, 1):"4 兵", (state[5][0], state[5][1], 2, 1):("5", "黄忠"),
               (state[6][0], state[6][1], 2, 1):("6", "马超"), (state[7][0], state[7][1], 2, 1):("7", "赵云"),
               (state[8][0], state[8][1], 2, 1):("8", "张飞"), (state[9][0], state[9][1], 2, 2):("9", "曹操"), (state[10][0], state[10][1], 1, 1):"k",
               (state[11][0], state[11][1], 1, 1):"k"}
    keys = diction.keys()
    for i in range(25):
        chosen = list(filter(lambda x: x[0] <= i / 5 and x[0] + x[2] > i / 5, keys))
        chosen = sorted(chosen, key = lambda x: x[1])
        line = ""
        for x in chosen:
            if diction[x] == "k":
                line += " " * 10
            elif i == x[0] * 5:
                if x[3] == 1:
                    line += "┌" + "━" * 3 + "┐"
                else:
                    line += "┌" + "━" * 8 + "┐"
            elif i == (x[0] + x[2]) * 5 - 1:
                if x[3] == 1:
                    line += "└" + "━" * 3 + "┘"
                else:
                    line += "└" + "━" * 8 + "┘"
            elif x[2] == 1 and i == x[0] * 5 + 2:
                line += "│" + " " * (4 * x[3] - 3) + diction[x] + " " * (4 * x[3] - 3) + "│"
            elif x[2] == 2 and i == x[0] * 5 + 4:
                line += "│" + " " * (5 * x[3] - 2) + diction[x][0] + " " * (5 * x[3] - 3) + "│"
            elif x[2] == 2 and i == x[0] * 5 + 5:
                line += "│" + " " * (5 * x[3] - 4) + diction[x][1] + " " * (5 * x[3] - 4) + "│"
            else:
                if x[3] == 1:
                    line += "│" + " " * 6 + "│"
                else:
                    line += "│" + " " * 16 + "│"
        print(line)
        

state = [(2, 1), (3, 1), (3, 2), (4, 0), (4, 3), (0, 0), (0, 3), (2, 0), (2, 3), (0, 1), (4, 1), (4, 2)]
#state = [(0, 1), (0, 3), (3, 1), (3, 3), (4, 1), (1, 3), (0, 0), (2, 0), (3, 2), (1, 1), (4, 0), (4, 3)]
print_out(state)
