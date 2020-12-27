blacklist = [[-1, -1]]
allowed = 0
for lo, hi in sorted(list(map(int, line.split("-"))) for line in open("resources/inputs/2016/day20.txt")):
    prev_lo, prev_hi = blacklist[-1]
    if lo <= prev_hi + 1:
        blacklist[-1][1] = max(hi, prev_hi)
    else:
        blacklist.append([lo, hi])
        allowed += lo - prev_hi - 1

print(blacklist[0][1] + 1)
print(allowed + 4294967295 - blacklist[-1][1])
