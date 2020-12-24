import re
from collections import Counter
from functools import reduce

MOVES = {
    "e": (+1, -1, 0),
    "w": (-1, +1, 0),
    "ne": (+1, 0, -1),
    "sw": (-1, 0, +1),
    "se": (0, -1, +1),
    "nw": (0, +1, -1),
}

def add(pos1, pos2):
    x, y, z = pos1
    dx, dy, dz = pos2
    return x + dx, y + dy, z + dz

def next_day(grid):
    c = Counter(add(pos, d) for pos in grid for d in MOVES.values())
    return {pos for pos, n in c.items() if n == 2 or n == 1 and pos in grid}

on = set()
for line in open("resources/inputs/2020/day24.txt"):
    on ^= {(reduce(add, map(MOVES.get, re.findall(r"e|se|sw|w|nw|ne", line)), (0, 0, 0)))}

print(len(on))
for _ in range(100):
    on = next_day(on)
print(len(on))
