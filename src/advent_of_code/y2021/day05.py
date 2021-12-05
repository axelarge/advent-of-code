import re

L = [list(map(int, re.findall(r"\d+", l))) for l in open("resources/inputs/2021/day05.txt")]

def solve(part2=False):
    maxx = max(max(l[0], l[2]) for l in L)
    maxy = max(max(l[1], l[3]) for l in L)
    cover = [[0] * (maxx + 1) for _ in range(maxy + 1)]
    for x1, y1, x2, y2 in L:
        dx = 0 if x1 == x2 else 1 if x1 < x2 else -1
        dy = 0 if y1 == y2 else 1 if y1 < y2 else -1
        if dx and dy and not part2:
            continue
        for i in range(max(abs(x2 - x1), abs(y2 - y1)) + 1):
            x = x1 + i * dx
            y = y1 + i * dy
            cover[y][x] += 1
    # print("\n".join(" ".join({0: "."}.get(s, str(s)) for s in l) for l in cover))

    return sum(n > 1 for row in cover for n in row)


print(solve())
print(solve(True))
