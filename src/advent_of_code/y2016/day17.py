from hashlib import md5
from heapq import heappush, heappop

DX = [0, 0, -1, +1]
DY = [-1, +1, 0, 0]
code = open("resources/inputs/2016/day17.txt").read().strip().encode()
shortest = None
max_len = 0
q = [(0, 0, 0, "")]
while q:
    _, x, y, path = heappop(q)
    if (x, y) == (3, 3):
        shortest = shortest or path
        max_len = max(max_len, len(path))
    else:
        for i, valid in enumerate(map("bcdef".__contains__, md5(code + path.encode()).hexdigest()[:4])):
            if valid:
                x1 = x + DX[i]
                y1 = y + DY[i]
                if 0 <= x1 < 4 and 0 <= y1 < 4:
                    heappush(q, (len(path), x1, y1, path + "UDLR"[i]))
print(shortest)
print(max_len)
