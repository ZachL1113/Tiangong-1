#横刀立马初始状态#
state = [(2, 1), (3, 1), (3, 2), (4, 0), (4, 3), (0, 0), (0, 3), (2, 0), (2, 3), (0, 1), (4, 1), (4, 2)]
#编号：大小，名字#
dic = [(1, 2, "关羽"), (1, 1, "1 兵"), (1, 1, "2 兵"), (1, 1, "3 兵"), (1, 1, "4 兵"),
           (2, 1, "黄忠"), (2, 1, "马超"), (2, 1, "赵云"), (2, 1, "张飞"), (2, 2, "曹操"),
           (1, 1, "k"), (1, 1, "k")]
#0不动1左2右3上4下#

#输出当前state对应界面#
def print_out(state = state, dic = dic):
    diction = [(x, y, h, w, str(idx), name) for idx, ((x, y),  (h, w, name)) in enumerate(zip(state, dic))]
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
#print_out()


#########################################################################################################



#check方块附近空格#
def check_k(n, state, dic):
    k = [(10, state[10]), (11, state[11])]
    now = state[n]
    yes = []
    for i in k:
        if ((i[1][0] == now[0] or i[1][0] == now[0] + dic[n][0] - 1) and (i[1][1] == now[1] - 1 or i[1][1] == now[1] + dic[n][1])) or ((i[1][1] == now[1] or i[1][1] == now[1] + dic[n][1] - 1) and (i[1][0] == now[0] - 1 or i[1][0] == now[0] + dic[n][0])):
            yes.append(i)#同行右同行左同列上同列下#
    return yes
#print(check_k(4, state, dic))

#朝一个方向动#
def move(n, state, dic, direction):
    k = [(10, state[10]), (11, state[11])]
    now = state[n]
    state1 = state.copy()
    if direction == 0:#不动#
        return None
    elif not (k[0] in check_k(k[1][0], state, dic) and k == check_k(n, state, dic)):#边长为1一侧可走#
        for i in k:
            if direction == 1 and i[1][0] == now[0] and i[1][1] == now[1] - 1 and dic[n][0] == 1:#左#
                state[n] = i[1]
                state[i[0]] = (now[0], now[1] + dic[n][1] - 1)
            elif direction == 2 and i[1][0] == now[0] and i[1][1] == now[1] + dic[n][1] and dic[n][0] == 1:#右#
                state[n] = (now[0], now[1] + 1)
                state[i[0]] = now
            elif direction == 3 and i[1][1] == now[1] and i[1][0] == now[0] - 1 and dic[n][1] == 1:#上#
                state[n] = i[1]
                state[i[0]] = (now[0] + dic[n][0] - 1, now[1])
            elif direction == 4 and i[1][1] == now[1] and i[1][0] == now[0] + dic[n][0] and dic[n][1] == 1:#下#
                state[n] = (now[0] + 1, now[1])
                state[i[0]] = now
    elif k[0] in check_k(k[1][0], state, dic) and k == check_k(n, state, dic):#边长为2一侧可走#
        if direction == 1 and k[0][1][1] == now[1] - 1 and dic[n][0] == 2:#左#
            state[n] = (state[n][0], state[n][1] - 1)
            state[k[0][0]] = (k[0][1][0], k[0][1][1] + dic[n][1])
            state[k[1][0]] = (k[1][1][0], k[1][1][1] + dic[n][1])
        elif direction == 2 and k[0][1][1] == now[1] + dic[n][1] and dic[n][0] == 2:#右#
            state[n] = (state[n][0], state[n][1] + 1)
            state[k[0][0]] = (k[0][1][0], k[0][1][1] - dic[n][1])
            state[k[1][0]] = (k[1][1][0], k[1][1][1] - dic[n][1])
        elif direction == 3 and k[0][1][0] == now[0] - 1 and dic[n][1] == 2:#上#
            state[n] = (state[n][0] - 1, state[n][1])
            state[k[0][0]] = (k[0][1][0] + dic[n][0], k[0][1][1])
            state[k[1][0]] = (k[1][1][0] + dic[n][0], k[1][1][1])
        elif direction == 4 and k[0][1][0] == now[0] + dic[n][0] and dic[n][1] == 2:#下#
            state[n] = (state[n][0] + 1, state[n][1])
            state[k[0][0]] = (k[0][1][0] - dic[n][0], k[0][1][1])
            state[k[1][0]] = (k[1][1][0] - dic[n][0], k[1][1][1])
#move(3, state, dic, 2)
#print_out(state)
#move(3, state, dic, 2)
#print_out(state)
#move(4, state, dic, 2)
#print_out(state)

#检查move能否运行#
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

#走一步or两步#
def move_steps(n, state, dic, direction1, direction2):
    if check_move(n, state, dic, direction1):
        move(n, state, dic, direction1)
    if check_move(n, state, dic, direction2):
        move(n, state, dic, direction2)
#move_steps(3, state, dic, 2, 2)
#move_steps(1, state, dic, 4, 2)
#move_steps(1, state, dic, 4, 0)
#print_out(state)
#print(move_steps(3, state, dic, 2, 2))

#检查move_steps能否运行#
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
                return False
    return True
#print(check_move_steps(3, state, dic, 2, 2))
#print(check_move_steps(3, state, dic, 1, 1))
#print(check_move_steps(4, state, dic, 1, 2))

#找出所有可行的走法#
def check(n, state, dic):
    mov = []
    for i in range(1, 5):
        for j in range(0, 5):
            if check_move_steps(n, state, dic, i, j) == True:
                mov.append((i, j))
    return mov
#print(check(3, state, dic))
#print(check(4, state, dic))
#print(check(0, state, dic))

#找出所有可能的下一步情况#
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

#print所有可能的下一步情况#
def print_all(state, dic):
    all_state = all_move(state, dic)
    for i in all_state:
        print_out(i, dic)
#print_all(state, dic)


####################################################################################################

#找出最终成功情况#
def success(state):
    if state[9] == (3, 1):
        return True
    return False

#找出最少步数#
def least_steps(state, dic):
    if success(state):
        return 0
    else:
        next_state = all_move(state, dic)
        least = 1 + least_steps(next_state[0], dic)
        for i in next_state:
            least = min(least, 1 + least_steps(i, dic))
        return least
#print(least_steps(state, dic))

dir_dic = {None: 0, "": 0, "L": 1, "l": 1, "R": 2, "r":2, "U": 3, "u": 3, "D": 4, "d": 4}#转换玩家输入方向信号#
state1 = state.copy()#保存初始状态#
#玩家一次操作#
def play(state, dic, dir_dic, next_num, next_move):
    if len(next_move) == 1 and check_play(state, dic, dir_dic, next_num, next_move):#一步#
        move_steps(int(next_num), state, dic, dir_dic[next_move], 0)
    elif len(next_move) == 2 and check_play(state, dic, dir_dic, next_num, next_move):#两步#
        move_steps(int(next_num), state, dic, dir_dic[next_move[0]], dir_dic[next_move[1]])
    else:
        print("指令不正确")
    print_out(state, dic)
#play(state, dic)

#检查play能否运行#
def check_play(state, dic, dir_dic, next_num, next_move):
    if len(next_move) == 1 and check_move_steps(int(next_num), state, dic, dir_dic[next_move], 0):
        return True
    elif len(next_move) == 2 and check_move_steps(int(next_num), state, dic, dir_dic[next_move[0]], dir_dic[next_move[1]]):
        return True
    else:
        return False
#print(check_play(state, dic, dir_dic, 1, "DR"))
#print(check_play(state, dic, dir_dic, 3, "D"))

#检查输入的方向指令#
def check_next_move(next_move):
    dir_keys = dir_dic.keys()
    if len(next_move) == 1 and next_move in dir_keys:
        return True
    elif len(next_move) == 2:
        for i in next_move:
            if i not in dir_keys:
                return False
        return True
    else:
        return False

#开玩#
def play_continue(state, dic, steps):
    past_steps = {0: state.copy()}
    steps = steps
    state1 = state.copy()
    print_out(state, dic)
    next_num = 0
    while not success(state1):
        print(f"当前已移动：{steps}步")
        next_num = input("请输入您想移动的棋子编号：（如想退出，请输入Q；如想回到上一步，请输入B；如想重置，请输入R；如想回到选关界面，请输入C）")
        dic_keys = [x for x in range(len(dic))]
        dic_keys = list(map(lambda x: str(x), dic_keys))
        if next_num == "Q" or next_num == "q":#退出#
            break
        elif next_num == "R" or next_num == "r":
            break
        elif next_num == "B" or next_num == "b":
            if steps == 0:
                print("已退回初始状态")
            else:
                state1 = past_steps[steps - 1]
                del past_steps[steps]
                steps -= 1
                print("已退回上一步")
            print_out(state1, dic)
        elif next_num == "C" or next_num == "c":
            break
        elif next_num in dic_keys: 
            if len(next_num) == 1:
                if len(check(int(next_num), state1, dic)) != 0:
                    print("L（左），R（右），U（上），D（下），多次移动请连续输入，例如：RR（右右），DL（下左），可不区分大小写")
                    next_move = input("请输入您想如何移动它：")
                    if check_next_move(next_move):
                        if check_play(state1, dic, dir_dic, next_num, next_move):
                            steps += 1
                            play(state1, dic, dir_dic, next_num, next_move)
                            past_steps[steps] = state.copy()
                        else:
                            print("指令无法执行")
                    else:
                        print("指令不正确")
                else:
                    print("该棋子无法移动")
            else:
                print("编号不正确")
        else:
            print("编号不正确")
    if next_num == "Q" or next_num == "q":
        print("已退出")
    elif next_num == "R" or next_num == "r":
        print("已重置")
        play_continue(state, dic, 0)
    elif next_num == "C" or next_num == "c":
        print("已回到选关界面")
        choose_play(states)
    else:
        print(f"成功！你一共用了{steps}步")
        choose_play(states)
#play_continue(state, dic, 0)

#关卡信息#
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
states = {0: ("0小试牛刀", state0, dic0), 1: ("1七步成诗", state1, dic1), 2: ("2海阔天空", state2, dic2), 3: ("3横刀立马", state3, dic3)}

#选关#
def choose_play(states):
    states_keys = list(map(lambda x: str(x), states.keys()))
    for i in range(len(states)):
        print(states[i][0])
    choose = input("请输入您想玩的关卡编号：(如想退出，请输入Q)")
    while choose not in states_keys:
        if choose == "Q" or choose == "q":
            break
        print("关卡编号不存在")
        for i in range(len(states)):
            print(states[i][0])
        choose = input("请输入您想玩的关卡编号：(如想退出，请输入Q)")
    if choose == "Q" or choose == "q":
        print("已退出")
    else:
        play_continue(states[int(choose)][1], states[int(choose)][2], 0)
if __name__ == '__main__':
    choose_play(states)