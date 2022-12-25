import re

F = open("resources/inputs/2022/day15.txt").read().splitlines()
D = []
for line in F:
    sx, sy, bx, by = map(int, re.findall(r"-?\d+", line))
    D.append((sx, sy, abs(sx - bx) + abs(sy - by)))


def merge(ranges):
    ranges = sorted(ranges)
    res = [ranges[0]]
    for lo, hi in ranges[1:]:
        if lo <= res[-1][1]:
            res[-1] = (res[-1][0], max(hi, res[-1][1]))
        else:
            res.append((lo, hi))
    return res


r = []
for sx, sy, d in D:
    m = d - abs(2000000 - sy)
    if m > 0:
        r.append([sx - m, sx + m])
part1 = sum(hi - lo for lo, hi in merge(r))
print(part1)
assert part1 == 5564017

A1, A2, B1, B2 = [set() for _ in range(4)]
for sx, sy, d in D:
    m = d + 1
    a = sx - sy  # diagonal /
    b = sx + sy  # diagonal \
    A1.add(a - m)  # /.
    A2.add(a + m)  # ./
    B1.add(b - m)  # \.
    B2.add(b + m)  # .\
a = next(iter(A1 & A2))
b = next(iter(B1 & B2))
x = (a + b) // 2
y = b - x
part2 = x * 4000000 + y
print(part2)
assert part2 == 11558423398893

# from collections import defaultdict
#
# def overlaps(*ranges):
#     events = []
#     for i, r in enumerate(ranges):
#         for lo, hi in r:
#             events.append((lo, i))
#             events.append((hi, i))
#     res = []
#     open = set()
#     for x, i in sorted(events):
#         was_open = len(open) == 2
#         open ^= {i}
#         is_open = len(open) == 2
#         if was_open ^ is_open:
#             res.append(x)
#     return res
#
# As, Bs, Cs, Ds = [defaultdict(list) for _ in range(4)]
# for sx, sy, d in D:
#     m = d + 1
#     a = sx - sy  # diagonal /
#     b = sx + sy  # diagonal \
#     As[a - m].append([b - m, b + m])  # /.
#     Bs[b - m].append([a - m, a + m])  # \.
#     Cs[a + m].append([b - m, b + m])  # ./
#     Ds[b + m].append([a - m, a + m])  # .\
#
# for coll in [As, Bs, Cs, Ds]:
#     for k in coll:
#         coll[k] = merge(coll[k])
# a_overlap = []
# for a in As:
#     if over := overlaps(As[a], Cs[a]):
#         a_overlap.append([a, over])
# b_overlap = []
# for b in Bs:
#     if over := overlaps(Bs[b], Ds[b]):
#         b_overlap.append([b, over])
# a = a_overlap[0][0]
# b = b_overlap[0][0]
# for a, (blo, bhi) in a_overlap:
#     for b, (alo, ahi) in b_overlap:
#         print((a, b))
#         if alo <= a <= ahi and blo <= b <= bhi:
#             x = (a + b) // 2
#             y = b - x
#             if 0 <= x <= 4000000 and 0 <= y <= 4000000:
#                 part2 = x * 4000000 + y
#                 print((a, b), (x, y), part2)
# print(part2)
# assert part2 == 11558423398893
