from state import State, levels
from userInterface import UserInterface
from command import Command


ui = UserInterface()
playing = True
while playing:
  level = ui.askLevel(levels)
  if level == 'Q':
    exit()
  else:
    state = State.stateOfLevel(int(level))
    history = [state]

    ms = {"U": "上", "D": "下", "R": "右", "L": "左"}
    while True:
      ui.show(state)
      command = ui.askCommand(history)
      if command.isMove:
        move = state.buildMove(command)
        state = state.clone()
        move.execute(state)
        history.append(state)
      else:
        if command.isChose:
          break
        elif command.isBack:
          history.pop()
          state = history[-1]
        elif command.isExit:
          exit()
        else:
          state = history[0]
          history = [state]
      if state.check_success():
        print(f"恭喜你，通关成功！你总共用了{len(history) - 1}步")
        break





  #for i in range(10):
      # print(f"第{i}号棋子，{'可以' if state.isPieceCanBePick(i) else '不能'}移动")
      #if state.isPieceCanBePick(i):
        #for first in ms.keys():
            #cmd = Command.moveCommand(str(i), first)
            #print(f"命令: {cmd}")
            #move = state.buildMove(cmd)
            #if move.isValid():
              #print(move)
              #for second in ms.keys():
                #cmd = Command.moveCommand(str(i), first + second)
                #print(f"命令: {cmd}")
                #move = state.buildMove(cmd)
                #if move.isValid():
                  #print(move)




