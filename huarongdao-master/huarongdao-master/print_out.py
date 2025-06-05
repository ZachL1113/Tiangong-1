CELL_WIDTH = 9
class print_out:
    def getSpace(self):
        return " " * 9
    
    def printShortUp(self):
        print("┌" + "━" * 7 + "┐")
    
    def printLongUp(self):
        print("┌" + "━" * 16 + "┐")

    def printShortDown(self):
        print("└" + "━" * 7 + "┘")

    def printLongDown(self):
        print("└" + "━" * 16 + "┘")
    
    def printCaoCaoNum(self):
        print("│" + " " * 8 + "9" + " " * 7 + "│")

    def printCaoCaoName(self):
        print("│" + " " * 6 + "曹操" + " " * 6 + "│")
    
    def printVerticalNum(self, num):
        print()