data = [(l[0], int(l[1:])) for l in open("resources/inputs/2020/day12.txt")]
MOVE = dict(
    N=lambda x, y, n: (x, y - n),
    S=lambda x, y, n: (x, y + n),
    E=lambda x, y, n: (x + n, y),
    W=lambda x, y, n: (x - n, y),
)

def solve(dx, dy, part):
    x, y = 0, 0
    for c, n in data:
        if c == "F":
            x += dx * n
            y += dy * n
        elif c == "R":
            for _ in range(n // 90):
                dx, dy = -dy, dx
        elif c == "L":
            for _ in range(n // 90):
                dx, dy = dy, -dx
        elif part == 1:
            x, y = MOVE[c](x, y, n)
        else:
            dx, dy = MOVE[c](dx, dy, n)
    return abs(x) + abs(y)

print(solve(1, 0, 1))
print(solve(10, -1, 2))
