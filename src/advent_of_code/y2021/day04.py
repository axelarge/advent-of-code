order, *boards = open("resources/inputs/2021/day04.txt").read().split("\n\n")
order = list(map(int, order.split(",")))
boards = [[list(map(int, line.split())) for line in board.splitlines()] for board in boards]

def try_mark(board, n):
    for y, line in enumerate(board):
        for x, nb in enumerate(line):
            if nb == n:
                board[y][x] = -1
                return x, y

def check_win(board, x, y):
    return (all(board[y1][x] == -1 for y1 in range(len(board)))
            or all(board[y][x1] == -1 for x1 in range(len(board[0]))))

winners = set()
for n in order:
    for bi, board in enumerate(boards):
        if bi in winners:
            continue
        found = try_mark(board, n)
        if found:
            x, y = found
            if check_win(board, x, y):
                winners.add(bi)
                if len(winners) == 1 or len(winners) == len(boards):
                    print(n * sum(x for line in board for x in line if x != -1))
