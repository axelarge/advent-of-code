import re
from collections import Counter
from functools import reduce

MOVES = {
    "e": (+1, -1),
    "w": (-1, +1),
    "ne": (+1, 0),
    "sw": (-1, 0),
    "se": (0, -1),
    "nw": (0, +1),
}

def add(pos1, pos2):
    return pos1[0] + pos2[0], pos1[1] + pos2[1]

def next_day(grid):
    c = Counter(add(pos, d) for pos in grid for d in MOVES.values())
    return {pos for pos, n in c.items() if n == 2 or n == 1 and pos in grid}

on = set()
for line in open("resources/inputs/2020/day24.txt"):
    on ^= {(reduce(add, map(MOVES.get, re.findall(r"e|se|sw|w|nw|ne", line)), (0, 0)))}

print(len(on))
for _ in range(100):
    on = next_day(on)
print(len(on))
