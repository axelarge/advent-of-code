def show(g):
    minx, maxx = min(x for x, _ in g), max(x for x, _ in g)
    miny, maxy = min(y for _, y in g), max(y for _, y in g)
    print("\n".join("".join(" #"[(x, y) in g] for x in range(minx, maxx + 1)) for y in range(miny, maxy + 1)))


A, B = open("resources/inputs/2021/day13.txt").read().split("\n\n")
G = {tuple(map(int, pos.split(","))) for pos in A.splitlines()}
folds = []
for a, b in (l.split("=") for l in B.splitlines()):
    folds.append((a[-1], int(b)))

for i, (axis, f) in enumerate(folds):
    G1 = set()
    for x, y in G:
        if axis == "x" and x > f:
            G1.add((f + f - x, y))
        elif axis == "y" and y > f:
            G1.add((x, f + f - y))
        else:
            G1.add((x, y))
    G = G1
    if i == 0:
        print(len(G))
show(G)
