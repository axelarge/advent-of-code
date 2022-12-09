F = open("resources/inputs/2022/day09.txt").read().splitlines()
DELTA = dict(U=(0, -1), D=(0, 1), L=(-1, 0), R=(1, 0))

knots = [[0, 0] for _ in range(10)]
visited1 = set()
visited2 = set()

for step in F:
    direction, dist = step.split()
    hdx, hdy = DELTA[direction]
    for i in range(int(dist)):
        head = knots[0]
        head[0] += hdx
        head[1] += hdy

        for j in range(1, len(knots)):
            knot = knots[j]
            dx = head[0] - knot[0]
            dy = head[1] - knot[1]
            if abs(dx) <= 1 and abs(dy) <= 1:
                break
            knot[0] += dx // abs(dx) if dx else 0
            knot[1] += dy // abs(dy) if dy else 0
            head = knot
        visited1.add(tuple(knots[1]))
        visited2.add(tuple(knots[-1]))

part1 = len(visited1)
print(part1)
assert part1 == 5878

part2 = len(visited2)
print(part2)
assert part2 == 2405
