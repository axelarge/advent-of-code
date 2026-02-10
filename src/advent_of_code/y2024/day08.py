from collections import defaultdict
from itertools import count

F = open("resources/inputs/2024/day08.txt").read().splitlines()
MAXX = len(F[0])
MAXY = len(F)
G = defaultdict(list)
for y, row in enumerate(F):
    for x, c in enumerate(row):
        if c != ".":
            G[c].append((x, y))

anti1 = set()
anti2 = set()
for k in G:
    xys = G[k]
    for i, (x1, y1) in enumerate(xys):
        for x2, y2 in xys:
            if (x1, y1) == (x2, y2):
                continue
            dx = x2 - x1
            dy = y2 - y1
            if 0 <= x2 + dx < MAXX and 0 <= y2 + dy < MAXY:
                anti1.add((x2 + dx, y2 + dy))
            for sign in [-1, 1]:
                for kk in count(0, sign):
                    if not (0 <= x1 + kk * dx < MAXX and 0 <= y1 + kk * dy < MAXY):
                        break
                    anti2.add((x1 + kk * dx, y1 + kk * dy))
print(len(anti1))
print(len(anti2))
