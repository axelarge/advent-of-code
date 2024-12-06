F = open("resources/inputs/2024/day06.txt").read().splitlines()
G = {(x, y) for y, row in enumerate(F) for x, c in enumerate(row) if c == "#"}
XY0 = next((x, y) for y, row in enumerate(F) for x, c in enumerate(row) if c == "^")
MAX_X = len(F[0])
MAX_Y = len(F)


def walk(g):
    x, y = XY0
    dx, dy = 0, -1
    seen = {}
    while 0 <= x < MAX_X and 0 <= y < MAX_Y:
        seen[(x, y)] = (dx, dy)
        while (x + dx, y + dy) in g:
            dx, dy = -dy, dx
        x += dx
        y += dy
        if seen.get((x, y)) == (dx, dy):
            return True, seen
    return False, seen


seen1 = walk(G)[1].keys()
print(len(seen1))
print(sum(walk(G | {(x, y)})[0] for (x, y) in seen1 - {XY0}))
