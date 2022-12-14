import re

F = open("resources/inputs/2022/day14.txt").read()
R = {}
for line in F.splitlines():
    prev = None
    for x1, y1 in zip(*[map(int, re.findall(r"\d+", line))] * 2):
        if prev is not None:
            (x2, y2) = prev
            for xx in range(min(x2, x1), max(x2, x1) + 1):
                for yy in range(min(y2, y1), max(y2, y1) + 1):
                    R[(xx, yy)] = "#"
        prev = (x1, y1)


def draw(rocks):
    minx = min(x for x, y in rocks)
    maxx = max(x for x, y in rocks)
    miny = min(y for x, y in rocks)
    maxy = max(y for x, y in rocks)

    for y in range(miny, maxy + 1):
        row = []
        for x in range(minx, maxx + 1):
            row.append(rocks.get((x, y), " "))
        print("".join(row))


def solve(part):
    rocks = {**R}
    minx = min(x for x, y in rocks)
    maxx = max(x for x, y in rocks)
    miny = min(y for x, y in rocks)
    maxy = max(y for x, y in rocks)

    if part == 2:
        maxy += 2
        dd = (maxy - miny) + 1
        for x in range(minx - dd, maxx + dd + 1):
            rocks[(x, maxy)] = "#"

    n = 0
    stop = False
    while not stop:
        x, y = 500, 0
        while True:
            if (x, y) in rocks:
                stop = True
                break

            if part == 1 and not (minx <= x <= maxx and y <= maxy):
                stop = True
                break

            if (x, y + 1) not in rocks:
                y += 1
            elif (x - 1, y + 1) not in rocks:
                x -= 1
                y += 1
            elif (x + 1, y + 1) not in rocks:
                x += 1
                y += 1
            else:
                n += 1
                rocks[(x, y)] = "o"
                break
        # draw(rocks)
    # draw(rocks)
    return n


part1 = solve(part=1)
print(part1)
assert part1 == 737

part2 = solve(part=2)
print(part2)
assert part2 == 28145
