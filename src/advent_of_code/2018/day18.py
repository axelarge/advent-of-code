from collections import Counter

grid = tuple(tuple(l.strip()) for l in open("resources/inputs/2018/day18.txt"))
h, w = len(grid), len(grid[0])

deltas = [(dx, dy) for dx in (-1, 0, 1) for dy in (-1, 0, 1) if dx or dy]
def neighbors(grid, x, y):
    return [grid[y][x] for x, y in ((x + dx, y + dy) for dx, dy in deltas) if 0 <= y < h if 0 <= x < w]


def next_state(c, around):
    if c == "." and around.count("|") >= 3:
        return "|"
    if c == "|" and around.count("#") >= 3:
        return "#"
    if c == "#" and not ("#" in around and "|" in around):
        return "."
    return c


def step(grid):
    return tuple(tuple(next_state(c, neighbors(grid, x, y)) for x, c in enumerate(l)) for y, l in enumerate(grid))


def stats(grid):
    c = Counter(c for l in grid for c in l)
    return c["#"] * c["|"]


i = 0
target = 1000000000
after10 = None
seen = {}
while i < target:
    if i == 10:
        after10 = grid
    seen[grid] = i
    grid = step(grid)
    i += 1
    j = seen.get(grid)
    if j and i + i - j < target:
        i = target - (target - i) % (i - j)
    # for l in grid:
    #     print("".join(l))
    # print(i)

print(stats(after10))
print(stats(grid))
