F = open("resources/inputs/2022/day10.txt").read().splitlines()
X = 1
T = 1
part1 = 0
crt = [" "] * (40 * 6)


def cycles(n):
    global T, part1
    for _ in range(n):
        if (T - 20) % 40 == 0:
            part1 += T * X
        if X - 1 <= (T - 1) % 40 <= X + 1:
            crt[T - 1] = "#"
        T += 1


for line in F:
    match line.split():
        case ["noop"]:
            cycles(1)
        case ["addx", arg]:
            cycles(2)
            X += int(arg)

print(part1)
assert part1 == 12560

print()
for row in zip(*[iter(crt)] * 40):
    print("".join(row))
