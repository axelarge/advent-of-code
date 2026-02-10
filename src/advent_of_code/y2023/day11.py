F = open("resources/inputs/2023/day11.txt").read().splitlines()

points = [(x, y) for y, row in enumerate(F) for x, c in enumerate(row) if c == "#"]
empty_rows = [int(all(c == "." for c in row)) for row in F]
empty_cols = [int(all(row[ri] == "." for row in F)) for ri in range(len(F[0]))]
for coll in [empty_rows, empty_cols]:
    for i in range(1, len(coll)):
        coll[i] += coll[i - 1]

res1 = 0
res2 = 0
k = int(1e6) - 1
for i, (x1, y1) in enumerate(points):
    for x2, y2 in points[i + 1:]:
        d = abs(x2 - x1) + abs(y2 - y1)
        edx = empty_cols[max(x1, x2)] - empty_cols[min(x1, x2)]
        edy = empty_rows[max(y1, y2)] - empty_rows[min(y1, y2)]
        res1 += d + edx + edy
        res2 += d + k * edx + k * edy

print(res1)
print(res2)
assert res1 == 9647174
assert res2 == 377318892554
