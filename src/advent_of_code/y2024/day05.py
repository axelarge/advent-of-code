from collections import defaultdict

F = open("resources/inputs/2024/day05.txt").read()
F1, F2 = F.split("\n\n")
ltr = defaultdict(set)
rtl = defaultdict(set)
for line in F1.splitlines():
    a, b = map(int, line.split("|"))
    ltr[a].add(b)
    rtl[b].add(a)

total1 = 0
total2 = 0
for line in F2.splitlines():
    xs = list(map(int, line.split(",")))
    valid = True
    for i, x in enumerate(xs):
        if any(y in ltr[x] for y in xs[:i]):
            valid = False
            break
    if valid:
        total1 += xs[len(xs) // 2]
    else:
        xs = set(xs)
        fixed = []
        while xs:
            for x in xs:
                if rtl[x] & xs <= set(fixed):
                    fixed.append(x)
                    xs.remove(x)
                    break
        total2 += fixed[len(fixed) // 2]

print(total1)
print(total2)
