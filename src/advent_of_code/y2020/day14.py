import re

IN = open("resources/inputs/2020/day14.txt").read()

mem = {}
mask_and = ~0
mask_or = 0
for l in IN.splitlines():
    if l.startswith("mem"):
        addr, val = map(int, re.findall(r"\d+", l))
        mem[addr] = val & mask_and | mask_or
    else:
        l = l[7:]
        mask_and = int(l.replace("X", "1"), 2)
        mask_or = int(l.replace("X", "0"), 2)
print(sum(mem.values()))

mem = {}
mask_or = 0
floating = []
for l in IN.splitlines():
    if l.startswith("mem"):
        addr, val = map(int, re.findall(r"\d+", l))
        addr |= mask_or
        for i in range(2 ** len(floating)):
            a1 = addr
            for j, b in enumerate(floating):
                if i & (1 << j):
                    a1 |= (1 << b)
                else:
                    a1 &= ~(1 << b)
            mem[a1] = val
    else:
        l = l[7:]
        mask_or = int(l.replace("X", "0"), 2)
        floating = list(reversed([36 - i - 1 for i, c in enumerate(l) if c == "X"]))
print(sum(mem.values()))
