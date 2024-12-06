def ray(g, x, y, dx, dy):
    n = 4
    while n:
        if c := g.get((x, y)):
            yield c
            x += dx
            y += dy
            n -= 1
        else:
            break


def rays(g, x, y):
    return ["".join(ray(g, x, y, dx, dy)) for dx in (-1, 0, 1) for dy in (-1, 0, 1) if dx or dy]


F = open("resources/inputs/2024/day04.txt").read().splitlines()
G = {(x, y): c for y, row in enumerate(F) for x, c in enumerate(row)}
total1 = 0
total2 = 0
for (x, y), c in G.items():
    if c == "X":
        total1 += sum(r == "XMAS" for r in rays(G, x, y))
    elif c == "A":
        tl, tr, bl, br = [G.get((x + dx, y + dy), "") for dx, dy in [(-1, -1), (+1, -1), (-1, +1), (+1, +1)]]
        total2 += all(s in ["MS", "SM"] for s in [tl + br, bl + tr])

print(total1)
print(total2)
