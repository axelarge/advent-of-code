import re
from collections import Counter
from math import prod

F = open("resources/inputs/2022/day11.txt").read()

monkeys = []
for raw in F.split("\n\n"):
    lines = raw.splitlines()
    monkeys.append((tuple(map(int, re.findall(r"\d+", lines[1]))),
                    lines[2].split(" = ")[1],
                    int(re.search(r"\d+", lines[3])[0]),
                    int(re.search(r"\d+", lines[4])[0]),
                    int(re.search(r"\d+", lines[5])[0])))

g = prod(m[2] for m in monkeys)


def solve(part1):
    inventory = [list(m[0]) for m in monkeys]
    times = Counter()
    for _ in range(20 if part1 else 10000):
        for i, (_, ops, test, true, false) in enumerate(monkeys):
            items = inventory[i]
            while items:
                times[i] += 1
                item = items.pop(0)
                item = eval(ops.replace("old", str(item)))
                if part1:
                    item = item // 3
                item = item % g
                inventory[false if item % test else true].append(item)
    return prod(v for _, v in times.most_common(2))


p1 = solve(True)
print(p1)
assert p1 == 58794

p2 = solve(False)
print(p2)
assert p2 == 20151213744
