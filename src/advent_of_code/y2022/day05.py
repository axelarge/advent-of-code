import re

F = open("resources/inputs/2022/day05.txt").read()
A, B = map(str.splitlines, F.split("\n\n"))
STACKS = [list(s[-2::-1].strip()) for s in list(map("".join, zip(*A)))[1::4]]
steps = [tuple(map(int, re.findall(r"\d+", line))) for line in B]


def solve(part1):
    stacks = [s[:] for s in STACKS]
    for n, fr, to in steps:
        xf = stacks[fr - 1][-n:]
        stacks[to - 1].extend(reversed(xf) if part1 else xf)
        del stacks[fr - 1][-n:]
    return "".join(list(c[-1] for c in stacks))


p1 = solve(True)
print(p1)
assert p1 == "JCMHLVGMG"

p2 = solve(False)
print(p2)
assert p2 == "LVMRWSSPZ"
