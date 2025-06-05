class MoveItem:
  def __init__(self, num, x, y):
    self.num = num
    self.x = x
    self.y = y

  def execute(self, state):#操作，将第self.num号棋子移动到(x, y)坐标上#
    p = state.pieces[self.num]
    p.x = self.x
    p.y = self.y

  def __str__(self):
      return f"将第{self.num % 10}号{'棋子' if self.num < 10 else '空格'}，移动到({self.x}, {self.y})"

class Move:
  def __init__(self, items = []):#items里的元素为piece#
    self.items = sorted(items, key = lambda x: int(x.num))

  def execute(self, state):
    for i in self.items:
      i.execute(state)
    
  def isValid(self):
    return len(self.items) > 1

  def changeAB(a, b):
    items = [MoveItem(a.num, b.x, b.y), MoveItem(b.num, a.x, a.y)]
    return Move(items)

  def with_A2B(self, a, b):
    self.items.append(MoveItem(a.num, b[0], b[1]))
    return self

  def a2b_b2c(a, b, c):
    items = [MoveItem(a.num, b.x, b.y), MoveItem(b.num, c[0], c[1])]
    return Move(items)

  def a2b_b2c_d2e(a, b, c, d, e):
    return Move.a2b_b2c(a, b, c).with_A2B(d, e)

  def __str__(self):
    return "\n".join(map(lambda x: str(x), self.items))
  #将一个iterable里的元素变成一个字符串，并在每个元素间插入指定分隔符，"\n"意为换行#