import re
from math import prod

F = open("resources/inputs/2023/day03.txt").read().splitlines()
G = {(x, y): c for y, line in enumerate(F) for x, c in enumerate(line) if c != "."}

res2 = 0
nums = {}
for (x, y), c in G.items():
    if not c.isdigit():
        near = {}
        for x1, y1 in ((x + dx, y + dy) for dx in (-1, 0, 1) for dy in (-1, 0, 1) if not dx == dy == 0):
            if G.get((x1, y1), "").isdigit():
                while G.get((x1 - 1, y1), "").isdigit():
                    x1 -= 1
                near[(x1, y1)] = int(re.search(r"\d+", F[y1][x1:])[0])
        nums.update(near)
        if c == "*" and len(near) == 2:
            res2 += prod(near.values())
res1 = sum(nums.values())

print(res1)
print(res2)
assert res1 == 514969
assert res2 == 78915902
