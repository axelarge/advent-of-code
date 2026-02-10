import re

F = open("resources/inputs/2023/day05.txt").read()
def ints(s):
    return list(map(int, re.findall(r"\d+", s)))


chunks = F.split("\n\n")
seeds = ints(chunks[0])
maps = []
for chunk in chunks[1:]:
    m = []
    for line in chunk.splitlines()[1:]:
        to, lo, n = ints(line)
        m.append([lo, lo + n, to - lo])
    maps.append(sorted(m))

# def combine1(lo, hi, d, m):
#     mlo, mhi, md = m
#     if hi < mlo or lo > mhi:
#         yield lo, hi, d
#     if lo < mlo:


def seed_loc(seed):
    x = seed
    for m in maps:
        for lo, hi, d in m:
            if lo <= x <= hi:
                x += d
                break
    return x


print(min(map(seed_loc, seeds)))

# seed_ranges = [seeds[i:i + 2] for i in range(0, len(seeds), 2)]
# res2 = None
# for lo, n in seed_ranges:
#     hi = lo+n
#     for mlo,mhi,_ in maps[0]:
#         if hi<mlo or lo>mhi:
#
