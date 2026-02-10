D = {k: v for v, k in enumerate("=-012", -2)}


def parse(s):
    res = 0
    times = 1
    for c in s[::-1]:
        res += times * D[c]
        times *= 5
    return res


def dump(n):
    res = []
    while n:
        n, digit = divmod(n + 2, 5)
        res.append("=-012"[digit])
    return "".join(res[::-1])


F = open("resources/inputs/2022/day25.txt").read().splitlines()
part1 = dump(sum(map(parse, F)))
print(part1)
assert part1 == "2=020-===0-1===2=020"
