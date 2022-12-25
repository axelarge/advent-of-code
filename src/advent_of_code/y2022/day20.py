def mix(data, times=1):
    data = list(enumerate(data))
    l = len(data)
    res = data[:]
    for _ in range(times):
        for xx in data:
            i = res.index(xx)
            x = xx[1]
            del res[i]
            res.insert((i + x) % (l - 1), xx)
    mixed = [xx[1] for xx in res]
    i = mixed.index(0)
    return sum(mixed[(i + d) % l] for d in [1000, 2000, 3000])


F = open("resources/inputs/2022/day20.txt").read().splitlines()
data = list(map(int, F))

part1 = mix(data)
print(part1)
assert part1 == 3700

part2 = mix([x * 811589153 for x in data], times=10)
print(part2)
assert part2 == 10626948369382
