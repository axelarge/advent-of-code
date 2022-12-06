F = open("resources/inputs/2022/day06.txt").read().strip()


def solve(n):
    for i in range(0, len(F) - n):
        if len(set(F[i + j] for j in range(n))) == n:
            return i + n


part1 = solve(4)
print(part1)
assert part1 == 1235

part2 = solve(14)
print(part2)
assert part2 == 3051
