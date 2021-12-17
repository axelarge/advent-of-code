import re
from math import ceil

IN = open("resources/inputs/2021/day17.txt").read()
tx1, tx2, ty1, ty2 = map(int, re.findall(r"-?\d+", IN))

best_height = -1
tot = 0
for vx0 in range(int(ceil(((1 + 8 * tx1) ** 0.5 - 1) / 2)), tx2 + 1):
    for vy0 in range(ty1, -ty1):
        x, y = 0, 0
        vx, vy = vx0, vy0
        highest = 0
        while x <= tx2 and y >= ty1:
            if y > highest:
                highest = y
            if x >= tx1 and y <= ty2:
                tot += 1
                if highest > best_height:
                    best_height = highest
                break
            x += vx
            y += vy
            if vx > 0:
                vx -= 1
            vy -= 1

print(best_height)
print(tot)
