data = [(l[0], int(l[1:])) for l in open("resources/inputs/2020/day12.txt")]
MOVE = dict(
    N=lambda x, y, n: (x, y - n),
    S=lambda x, y, n: (x, y + n),
    E=lambda x, y, n: (x + n, y),
    W=lambda x, y, n: (x - n, y),
)

heading = 'E'
x, y = 0, 0
for c, n in data:
    if c == "F":
        c = heading
    if c == "R":
        for _ in range(n // 90):
            heading = dict(E="S", S="W", W="N", N="E")[heading]
    elif c == "L":
        for _ in range(n // 90):
            heading = dict(E="N", S="E", W="S", N="W")[heading]
    else:
        x, y = MOVE[c](x, y, n)
print(abs(x) + abs(y))

x, y = 0, 0
wpx, wpy = 10, -1
for c, n in data:
    if c == "F":
        x += wpx * n
        y += wpy * n
    elif c == "R":
        for _ in range(n // 90):
            wpx, wpy = -wpy, wpx
    elif c == "L":
        for _ in range(n // 90):
            wpx, wpy = wpy, -wpx
    else:
        wpx, wpy = MOVE[c](wpx, wpy, n)
print(abs(x) + abs(y))
