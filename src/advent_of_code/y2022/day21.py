import functools
import operator
from z3 import z3

F = open("resources/inputs/2022/day21.txt").read().splitlines()
NUMS = {}
DEPS = {}
OPS = {"+": operator.add, "-": operator.sub, "*": operator.mul, "/": operator.floordiv}
for line in F:
    name, *words = line.split()
    name = name[:-1]
    if len(words) == 1:
        NUMS[name] = int(words[0])
    else:
        DEPS[name] = words


@functools.cache
def resolve(name):
    if name in NUMS:
        return NUMS[name]
    else:
        a, op, b = DEPS[name]
        return OPS[op](resolve(a), resolve(b))


part1 = resolve("root")
print(part1)
assert part1 == 54703080378102

V = {name: z3.Int(name) for name in list(NUMS.keys()) + list(DEPS.keys())}
s = z3.Optimize()
for name in NUMS:
    if name != "humn":
        s.add(V[name] == NUMS[name])
for name in DEPS:
    a, op, b = DEPS[name]
    if name == "root":
        s.add(V[a] == V[b])
    elif op == "/":
        s.add(V[name] == V[a] / V[b])
    else:
        s.add(V[name] == OPS[op](V[a], V[b]))
humn = V["humn"]
s.minimize(humn)
s.check()
part2 = s.model()[humn].as_long()
print(part2)
assert part2 == 3952673930912
