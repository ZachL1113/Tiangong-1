a = "┌" + "━" * 3 + "┐"
b = "┌" + "━" * 8 + "┐"
c = "│" + " " * 6 + "│"
d = "│" + " " * 16 + "│"
e = "└" + "━" * 3 + "┘"
f = "└" + "━" * 8 + "┘"
g = " " * 10
def print_out(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x):
    diction = {(a, b, 1, 2):"0 关羽", (c, d, 1, 1):"1 兵", (e, f, 1, 1):"2 兵", (g, h, 1, 1):"3 兵", (i, j, 1, 1):"4 兵", (k, l, 2, 1):("5", "黄忠"),
               (m, n, 2, 1):("6", "马超"), (o, p, 2, 1):("7", "赵云"), (q, r, 2, 1):("8", "张飞"), (s, t, 2, 2):("9", "曹操"), (u, v, 1, 1):"k",
               (w, x, 1, 1):"k"}
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
        

#print_out(2, 1, 3, 1, 3, 2, 4, 0, 4, 3, 0, 0, 0, 3, 2, 0, 2, 3, 0, 1, 4, 1, 4, 2)
print_out(0, 1, 0, 3, 3, 1, 3, 3, 4, 1, 1, 3, 0, 0, 2, 0, 3, 2, 1, 1, 4, 0, 4, 3)
