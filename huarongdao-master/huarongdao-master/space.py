class SpaceAnalyzer:
    def __init__(self, spaces):
        # 先将两个空格从上到下，从左到右排序
        spaceA, spaceB = sorted(spaces, key = lambda p: (p.y, p.x))
        self.spaceA = spaceA
        self.spaceB = spaceB
        self.isHighClosed = spaceA.isOnOf(spaceB) or spaceB.isOnOf(spaceA)
        self.isWideClosed = spaceA.isLeftOf(spaceB) or spaceB.isLeftOf(spaceA)
        self.isClosed = self.isHighClosed or self.isWideClosed
        self.notClosed = not self.isClosed

    def __str__(self):
        return f"两个空格{'上下' if self.isHighClosed else '左右' if self.isWideClosed else '不'}相邻"