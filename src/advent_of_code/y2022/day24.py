import functools
from math import lcm

F = open("resources/inputs/2022/day24.txt").read().splitlines()
H = len(F) - 2
W = len(F[0]) - 2
T = lcm(W, H)
B = []
DIR = {">": (1, 0), "<": (-1, 0), "^": (0, -1), "v": (0, 1)}
D = list(DIR.values())
for y, line in enumerate(F):
    for x, c in enumerate(line):
        if d := DIR.get(c):
            B.append((x - 1, y - 1, *d))


@functools.cache
def blizzards_at(t):
    return {(1 + (bx + dx * t) % W, 1 + (by + dy * t) % H) for bx, by, dx, dy in B}


def occupied(pos, t):
    return pos in blizzards_at(t % T)


def solve(t, start, end):
    current = [start]
    while current:
        next_ = set()
        for pos in current:
            if pos == end:
                return t
            x, y = pos
            for dx, dy in D:
                x1, y1 = x + dx, y + dy
                pos1 = x1, y1
                if 0 < y1 <= H and 0 < x1 <= W or pos1 == end:
                    if not occupied(pos1, t + 1):
                        next_.add(pos1)
            if not occupied(pos, t + 1):
                next_.add(pos)
        t += 1
        current = next_


start = (1, 0)
goal = (W, H + 1)

part1 = solve(0, start, goal)
print(part1)
assert part1 == 297

part2 = solve(solve(part1, goal, start), start, goal)
print(part2)
assert part2 == 856
