import re

IN = open("resources/inputs/2020/day14.txt").read()

mem1 = {}
mem2 = {}
mask_and = ~0
mask_or = 0
floating = []
for l in IN.splitlines():
    if l.startswith("mem"):
        addr, val = map(int, re.findall(r"\d+", l))
        mem1[addr] = val & mask_and | mask_or
        for i in range(2 ** len(floating)):
            addr2 = addr | mask_or
            for j, b in enumerate(floating):
                if i & (1 << j):
                    addr2 |= (1 << b)
                else:
                    addr2 &= ~(1 << b)
            mem2[addr2] = val
    else:
        l = l[7:]
        mask_and = int(l.replace("X", "1"), 2)
        mask_or = int(l.replace("X", "0"), 2)
        floating = [i for i, c in enumerate(reversed(l)) if c == "X"]

print(sum(mem1.values()))
print(sum(mem2.values()))
