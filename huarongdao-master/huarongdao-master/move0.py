def print_out(state):
    diction = {(state[0][0],state[0][1], 1, 2):"0 关羽", (state[1][0], state[1][1], 1, 1):"1 兵", (state[2][0], state[2][1], 1, 1):"2 兵",
               (state[3][0], state[3][1], 1, 1):"3 兵",(state[4][0], state[4][1], 1, 1):"4 兵", (state[5][0], state[5][1], 2, 1):("5", "黄忠"),
               (state[6][0], state[6][1], 2, 1):("6", "马超"), (state[7][0], state[7][1], 2, 1):("7", "赵云"),
               (state[8][0], state[8][1], 2, 1):("8", "张飞"), (state[9][0], state[9][1], 2, 2):("9", "曹操"),
               (state[10][0], state[10][1], 1, 1):"k", (state[11][0], state[11][1], 1, 1):"k"}
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
dic = {0: (1, 2, "关羽"), 1:(1, 1, "兵"), 2: (1, 1, "兵"), 3: (1, 1, "兵"), 4: (1, 1, "兵"),
           5: (2, 1, "黄忠"), 6: (2, 1, "马超"), 7: (2, 1, "赵云"), 8: (2, 1, "张飞"), 9:(2, 2, "曹操"),
           10: (1, 1, "k"), 11: (1, 1, "k")}
#0不动1左2右3上4下



def check_k(n, state, dic):
    k = [(10, state[10]), (11, state[11])]
    now = state[n]
    yes = []
    for i in k:
        if ((i[1][0] == now[0] or i[1][0] == now[0] + dic[n][0] - 1) and (i[1][1] == now[1] - 1 or i[1][1] == now[1] + dic[n][1])) or ((i[1][1] == now[1] or i[1][1] == now[1] + dic[n][1] - 1) and (i[1][0] == now[0] - 1 or i[1][0] == now[0] + dic[n][0])):
            yes.append(i)
    return yes
#print(check_k(4, state, dic))

def move(n, state, dic, direction):
    k = [(10, state[10]), (11, state[11])]
    now = state[n]
    state1 = state.copy()
    if direction == 0:
        return None
    elif not (k[0] in check_k(k[1][0], state, dic) and k == check_k(n, state, dic)):
        for i in k:
            if direction == 1 and i[1][0] == now[0] and i[1][1] == now[1] - 1 and dic[n][0] == 1:
                state[n] = i[1]
                state[i[0]] = (now[0], now[1] + dic[n][1] - 1)
            elif direction == 2 and i[1][0] == now[0] and i[1][1] == now[1] + dic[n][1] and dic[n][0] == 1:
                state[n] = (now[0], now[1] + 1)
                state[i[0]] = now
            elif direction == 3 and i[1][1] == now[1] and i[1][0] == now[0] - 1 and dic[n][1] == 1:
                state[n] = i[1]
                state[i[0]] = (now[0] + dic[n][0] - 1, now[1])
            elif direction == 4 and i[1][1] == now[1] and i[1][0] == now[0] + dic[n][0] and dic[n][1] == 1:
                state[n] = (now[0] + 1, now[1])
                state[i[0]] = now
    elif k[0] in check_k(k[1][0], state, dic) and k == check_k(n, state, dic):
        if direction == 1 and k[0][1][1] == now[1] - 1 and dic[n][0] == 2:
            state[n] = (state[n][0], state[n][1] - 1)
            state[k[0][0]] = (k[0][1][0], k[0][1][1] + 1)
            state[k[1][0]] = (k[1][1][0], k[1][1][1] + 1)
        elif direction == 2 and k[0][1][1] == now[1] + dic[n][1] and dic[n][0] == 2:
            state[n] = (state[n][0], state[n][1] + 1)
            state[k[0][0]] = (k[0][1][0], k[0][1][1] - 1)
            state[k[1][0]] = (k[1][1][0], k[1][1][1] - 1)
        elif direction == 3 and k[0][1][0] == now[0] - 1 and dic[n][1] == 2:
            state[n] = (state[n][0] - 1, state[n][1])
            state[k[0][0]] = (k[0][1][0] + 1, k[0][1][1])
            state[k[1][0]] = (k[1][1][0] + 1, k[1][1][1])
        elif direction == 4 and k[0][1][0] == now[0] + dic[n][0] and dic[n][1] == 2:
            state[n] = (state[n][0] + 1, state[n][1])
            state[k[0][0]] = (k[0][1][0] - 1, k[0][1][1])
            state[k[1][0]] = (k[1][1][0] - 1, k[1][1][1])
    if state == state1:
        return False
#move(3, state, dic, 2)
#print_out(state)
#move(3, state, dic, 2)
#print_out(state)
#move(4, state, dic, 2)
#print_out(state)

def check_move(n, state, dic, direction):
    k = [(10, state[10]), (11, state[11])]
    now = state[n]
    if direction == 0:
        return True
    elif not (k[0] in check_k(k[1][0], state, dic) and k == check_k(n, state, dic)):
        for i in k:
            if direction == 1 and i[1][0] == now[0] and i[1][1] == now[1] - 1 and dic[n][0] == 1:
                return True
            elif direction == 2 and i[1][0] == now[0] and i[1][1] == now[1] + dic[n][1] and dic[n][0] == 1:
                return True
            elif direction == 3 and i[1][1] == now[1] and i[1][0] == now[0] - 1 and dic[n][1] == 1:
                return True
            elif direction == 4 and i[1][1] == now[1] and i[1][0] == now[0] + dic[n][0] and dic[n][1] == 1:
                return True
        return False
    elif k[0] in check_k(k[1][0], state, dic) and k == check_k(n, state, dic):
        if direction == 1 and k[0][1][1] == now[1] - 1 and dic[n][0] == 2:
            return True
        elif direction == 2 and k[0][1][1] == now[1] + dic[n][1] and dic[n][0] == 2:
            return True
        elif direction == 3 and k[0][1][0] == now[0] - 1 and dic[n][1] == 2:
            return True
        elif direction == 4 and k[0][1][0] == now[0] + dic[n][0] and dic[n][1] == 2:
            return True
        return False
#print(check_move(3, state, dic, 1))
#print(check_move(3, state, dic, 2))
#print(check_move(4, state, dic, 1))
#print(check_move(3, state, dic, 0))

def move_steps(n, state, dic, direction1, direction2):
    if check_move(n, state, dic, direction1):
        move(n, state, dic, direction1)
    else:
        return False
    if check_move(n, state, dic, direction2):
        move(n, state, dic, direction2)
    else:
        return False
#move_steps(3, state, dic, 2, 2)
#move_steps(1, state, dic, 4, 2)
#move_steps(1, state, dic, 4, 0)
#print_out(state)
#print(move_steps(3, state, dic, 2, 2))

def check_move_steps(n, state, dic, direction1, direction2):
    state1 = state.copy()
    if not check_move(n, state, dic, direction1):
        return False
    else:
        move(n, state1, dic, direction1)
        if not check_move(n, state1, dic, direction2):
            return False
        else:
            move(n, state1, dic, direction2)
            if state1 == state:
                return "repeat"
    return True
#print(check_move_steps(3, state, dic, 2, 2))
#print(check_move_steps(3, state, dic, 1, 1))
#print(check_move_steps(4, state, dic, 1, 2))

def check(n, state, dic):
    mov = []
    for i in range(1, 5):
        for j in range(0, 5):
            if check_move_steps(n, state, dic, i, j) == True:
                mov.append((i, j))
    return mov
#print(check(3, state, dic))
#print(check(4, state, dic))
#print(check(1, state, dic))

def all_move(state, dic):
    all_state = []
    for i in range(10):
        if len(check(i, state, dic)) == 0:
            continue
        else:
            for j in check(i, state, dic):
                state1 = state.copy()
                move_steps(i, state1, dic, j[0], j[1])
                all_state.append(state1)
    return all_state
#print_out(all_move(state, dic)[1])

def print_all(state, dic):
    all_state = all_move(state, dic)
    for i in all_state:
        print_out(i)
#print_all(state, dic)
