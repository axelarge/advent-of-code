import re
from math import prod

F = open("resources/inputs/2022/day11.txt").read()

monkeys = []
for raw in F.split("\n\n"):
    lines = raw.splitlines()
    monkeys.append((list(map(int, re.findall(r"\d+", lines[1]))),
                    [int(x) if x.isnumeric() else x for x in lines[2].split()[-3:]],
                    *(int(re.search(r"\d+", line)[0]) for line in lines[3:])))
M = prod(m[2] for m in monkeys)

def solve(part1):
    inventory = [m[0][:] for m in monkeys]
    times = [0] * len(monkeys)
    for _ in range(20 if part1 else 10000):
        for i, (_, ops, test, true, false) in enumerate(monkeys):
            items = inventory[i]
            for item in items:
                times[i] += 1
                a, op, b = ops
                a = item if a == "old" else a
                b = item if b == "old" else b
                item = a * b if op == "*" else a + b
                if part1:
                    item = item // 3
                item = item % M
                inventory[false if item % test else true].append(item)
            items.clear()
    return prod(sorted(times)[-2:])


p1 = solve(True)
print(p1)
assert p1 == 58794

p2 = solve(False)
print(p2)
assert p2 == 20151213744
