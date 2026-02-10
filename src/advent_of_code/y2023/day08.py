import re
import math
from itertools import cycle

F = open("resources/inputs/2023/day08.txt").read().splitlines()
commands = F[0]
G = {a: (b, c) for a, b, c in (re.findall(r"\w+", line) for line in F[2:])}


def solve(pos, part2=False):
    for i, command in enumerate(cycle(commands)):
        if pos == "ZZZ" or part2 and pos.endswith("Z"):
            return i
        pos = G[pos][command == "R"]


res1 = solve("AAA")
res2 = math.lcm(*(solve(pos, True) for pos in G if pos.endswith("A")))

print(res1)
print(res2)
assert res1 == 23147
assert res2 == 22289513667691
