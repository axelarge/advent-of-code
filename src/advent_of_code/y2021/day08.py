IN = [(line[:10], line[11:]) for line in map(str.split, open("resources/inputs/2021/day08.txt"))]

tot1 = 0
tot2 = 0
for l, r in IN:
    tot1 += sum(len(d) in (2, 3, 4, 7) for d in r)
    patterns = list(map(frozenset, l))

    def find(n, f=lambda _: True):
        return next(p for p in patterns if len(p) == n and f(p))

    p1 = find(2)
    p4 = find(4)
    p7 = find(3)
    p8 = find(7)

    p6 = find(6, lambda p: not (p > p1))
    p9 = find(6, p4.issubset)
    p0 = find(6, lambda p: p != p6 and p != p9)

    p3 = find(5, p1.issubset)
    p5 = find(5, lambda p: p | p1 == p9)
    p2 = find(5, lambda p: p != p3 and p != p5)

    digits = {d: str(i) for i, d in enumerate((p0, p1, p2, p3, p4, p5, p6, p7, p8, p9))}
    tot2 += int("".join(digits[frozenset(d)] for d in r))

print(tot1)
print(tot2)
