import functools
from collections import deque
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


def solve(t0, start, end):
    q = deque([(t0, start)])
    seen = set()
    while q:
        state = q.popleft()
        if state not in seen:
            seen.add(state)
            t, pos = state
            if pos == end:
                return t
            x, y = pos
            for dx, dy in D:
                x1, y1 = x + dx, y + dy
                pos1 = x1, y1
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
