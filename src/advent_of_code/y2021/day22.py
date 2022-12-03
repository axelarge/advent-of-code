import re

IN = open("resources/inputs/2021/day22.txt").read()

steps = []
for line in IN.splitlines():
    on = line.startswith("on")
    x1, x2, y1, y2, z1, z2 = map(int, re.findall(r"-?\d+", line))
    steps.append((on, x1, x2, y1, y2, z1, z2))


points = set()
_, minx, maxx, miny, maxy, minz, maxz = 0, -50, 50, -50, 50, -50, 50
for on, x1, x2, y1, y2, z1, z2 in steps:
    if (x1 >= minx and x2 <= maxx or x1 <= maxx and x2 >= minx) and \
            (y1 >= miny and y2 <= maxy or y1 <= maxy and y2 >= miny) and \
            (z1 >= minz and z2 <= maxz or z1 <= maxz and z2 >= minz):
        new = set((x, y, z) for x in range(x1, x2 + 1) for y in range(y1, y2 + 1) for z in range(z1, z2 + 1))
        if on:
            points.update(new)
        else:
            points.difference_update(new)
print(len(points))

def solve(steps):
    prev = []
    for step in steps:
        on, x1, x2, y1, y2, z1, z2 = step
        bonus = []
        for oon, ox1, ox2, oy1, oy2, oz1, oz2 in prev:
            if x1 <= ox2 and x2 >= ox1 and y1 <= oy2 and y2 >= oy1 and z1 <= oz2 and z2 >= oz1:
                bonus.append((not oon, max(x1, ox1), min(x2, ox2), max(y1, oy1), min(y2, oy2), max(z1, oz1), min(z2, oz2)))
        prev.extend(bonus)
        if on:
            prev.append(step)

    tot = 0
    for on, x1, x2, y1, y2, z1, z2 in prev:
        tot += (1 if on else -1) * (x2 - x1 + 1) * (y2 - y1 + 1) * (z2 - z1 + 1)
    return tot


# print(solve([(on, max(x1, -50), min(x2, 50), max(y1, -50), min(y2, 50), max(z1, -50), min(z2, 50)) for on, x1, x2, y1, y2, z1, z2 in steps]))
print(solve(steps))
