import functools
from collections import deque
from math import lcm

F = open("resources/inputs/2022/day24.txt").read().splitlines()
H = len(F) - 2
W = len(F[0]) - 2
T = lcm(W, H)
B = []
DIR = {">": (1, 0), "<": (-1, 0), "^": (0, -1), "v": (0, 1)}
for y, line in enumerate(F):
    for x, c in enumerate(line):
        if d := DIR.get(c):
            B.append(((x, y), d))


@functools.cache
def blizzards_at(t):
    res = set()
    for (bx, by), (dx, dy) in B:
        xx = 1 + (bx - 1 + dx * t) % W
        yy = 1 + (by - 1 + dy * t) % H
        res.add((xx, yy))
    return res


def occupied(pos, t):
    return pos in blizzards_at(t % T)


def solve(t0, start, end):
    q = deque([(t0, start)])
    seen = set()
    while q:
        t, pos = q.popleft()
        state = (t % T, pos)
        if state not in seen:
            seen.add(state)
            if pos == end:
                return t
            for dx, dy in DIR.values():
                pos1 = pos[0] + dx, pos[1] + dy
                x1, y1 = pos1
                if 0 < y1 <= H and 0 < x1 <= W or pos1 == end:
                    if not occupied(pos1, t + 1):
                        q.append((t + 1, pos1))
            if not occupied(pos, t + 1):
                q.append((t + 1, pos))


start = (1, 0)
goal = (W, H + 1)

part1 = solve(0, start, goal)
print(part1)
assert part1 == 297

part2 = solve(solve(part1, goal, start), start, goal)
print(part2)
assert part2 == 856
