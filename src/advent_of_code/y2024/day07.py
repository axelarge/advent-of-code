import re


def solve(n, xs, part2=False):
    if len(xs) == 1:
        return n == xs[0]
    if not xs:
        return n == 0
    x = xs[0]
    if n % x == 0 and solve(n // x, xs[1:], part2=part2):
        return True
    if n >= x and solve(n - x, xs[1:], part2=part2):
        return True
    if part2:
        sn = str(n)
        sx = str(x)
        if sn.endswith(sx) and sn != sx and solve(int(str(n)[:-(len(sx))]), xs[1:], part2=part2):
            return True
    return False


F = open("resources/inputs/2024/day07.txt").read().splitlines()
tot1 = 0
tot2 = 0
for line in F:
    n, *xs = map(int, re.findall(r"\d+", line))
    if solve(n, xs[::-1]):
        tot1 += n
    if solve(n, xs[::-1], part2=True):
        tot2 += n

print(tot1)
print(tot2)
