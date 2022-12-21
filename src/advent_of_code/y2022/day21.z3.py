import functools
import operator
from z3 import z3

F = open("resources/inputs/2022/day21.txt").read().splitlines()
NUMS = {}
DEPS = {}
OPS = {"+": operator.add, "-": operator.sub, "*": operator.mul, "/": operator.truediv}
V = {}
for line in F:
    name, *words = line.split()
    name = name[:-1]
    V[name] = z3.Int(name)
    if len(words) == 1:
        NUMS[name] = int(words[0])
    else:
        DEPS[name] = words


def setup(part):
    s = z3.Optimize()
    for name in NUMS:
        if not (part == 2 and name == "humn"):
            s.add(V[name] == NUMS[name])
    for name in DEPS:
        a, op, b = DEPS[name]
        if part == 2 and name == "root":
            s.add(V[a] == V[b])
        else:
            s.add(V[name] == OPS[op](V[a], V[b]))
    return s


s = setup(part=1)
s.check()
part1 = s.model()[V["root"]].as_long()
print(part1)
assert part1 == 54703080378102

s = setup(part=2)
s.minimize(V["humn"])
s.check()
part2 = s.model()[V["humn"]].as_long()
print(part2)
assert part2 == 3952673930912
