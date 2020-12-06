lo, hi, total = 1024, 0, 0
for line in open("resources/inputs/2020/day05.txt").readlines():
    i = int(line.translate(str.maketrans("FBLR", "0101")), 2)
    lo = min(lo, i)
    hi = max(hi, i)
    total += i

print(hi)
print((hi - lo + 1) * (lo + hi) // 2 - total)
