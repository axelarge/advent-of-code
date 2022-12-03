from collections import defaultdict
from itertools import cycle
from math import gcd
from cmath import phase


def normalize(x, y):
    g = abs(gcd(x, y))
    return x // g, y // g


def dist(pos1, pos2):
    (x1, y1), (x2, y2) = pos1, pos2
    return abs(x2 - x1) + abs(y2 - y1)


def visible_from(pos):
    slopes = defaultdict(list)
    for pos1 in sorted(G, key=lambda pos1: dist(pos, pos1))[1:]:
        (x, y), (x1, y1) = pos, pos1
        slopes[normalize(x1 - x, y1 - y)].append(pos1)
    return slopes


def angle(slope):
    dx, dy = slope
    a = phase(-dy + dx * 1j)
    if a < 0:
        a += 5
    return a


IN = open("resources/inputs/2019/day10.txt").read().splitlines()
G = {(x, y) for y, row in enumerate(IN) for x, a in enumerate(row) if a != "."}

visible = max(map(visible_from, G), key=len)
print(len(visible))

i = 0
for s in cycle(sorted(visible, key=angle)):
    if visible[s]:
        x, y = visible[s].pop(0)
        i += 1
        if i == 200:
            print(x * 100 + y)
            break
