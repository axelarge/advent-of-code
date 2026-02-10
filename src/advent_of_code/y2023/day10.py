import re
from collections import OrderedDict, defaultdict, deque, Counter
from itertools import count, permutations, combinations
from heapq import heappush, heappop
from functools import lru_cache

F = open("resources/inputs/2023/day10.txt").read().splitlines()

G = {}
S = None
for y, line in enumerate(F):
    for x, c in enumerate(line):
        if c == "S":
            S = (x, y)
        if c != ".":
            G[(x, y)] = c


def connected(pos):
    x, y = pos
    if c := G.get(pos):
        if c in "|LJ":
            yield x, y - 1
        if c in "|7F":
            yield x, y + 1
        if c in "-J7":
            yield x - 1, y
        if c in "-LF":
            yield x + 1, y


def neighbors(pos):
    x, y = pos
    return (x, y - 1), (x + 1, y), (x, y + 1), (x - 1, y)


G[S] = {(0, 0, 1, 1): "7",
        (0, 1, 0, 1): "-",
        (0, 1, 1, 0): "F",
        (1, 0, 0, 1): "J",
        (1, 0, 1, 0): "|",
        (1, 1, 0, 0): "L"}[tuple(S in connected(xy) for xy in neighbors(S))]

dist = {}
Q = deque([(S, 0)])
while Q:
    xy, d = Q.popleft()
    if xy in dist:
        dist[xy] = min(dist[xy], d)
    else:
        dist[xy] = d
        for xy1 in connected(xy):
            Q.append((xy1, d + 1))

print(max(dist.values()))

res2 = 0
inside = False
prev = ""
for y, line in enumerate(F):
    for x, c in enumerate(line):
        if (x, y) == S:
            c = G[S]
        if (x, y) in dist:
            if c == "|" or prev + c in ["L7", "FJ"]:
                inside = not inside
            elif c in "FL":
                prev = c
        elif inside:
            res2 += 1
print(res2)
