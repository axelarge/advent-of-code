from itertools import product
from collections import Counter

def neighbors(p):
    return (p1 for p1 in product(*[(w - 1, w, w + 1) for w in p]) if p1 != p)

def step(state):
    c = Counter(near for pos in state for near in neighbors(pos))
    return {pos for pos, n in c.items() if n == 3 or n == 2 and pos in state}

IN = open("resources/inputs/2020/day17.txt")
state = {(x, y, 0) for y, row in enumerate(IN) for x, c in enumerate(row) if c == "#"}
state4 = {(x, y, z, 0) for x, y, z in state}

print(len(step(step(step(step(step(step(state))))))))
print(len(step(step(step(step(step(step(state4))))))))
