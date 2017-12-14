def connected(m, x, y, con=None):
    if con is None:
        con = set()
    if 0 <= x < 128 and 0 <= y < 128 and (x, y) not in con and m[y][x] == "1":
        con.add((x, y))
        for (xd, yd) in [(-1, 0), (0, -1), (1, 0), (0, 1)]:
            connected(m, x + xd, y + yd, con)
    return con


def solve(bmp):
    seen = set()
    gc = 0
    for y in xrange(128):
        for x in xrange(128):
            if bmp[y][x] == "1" and (x, y) not in seen:
                seen.update(connected(bmp, x, y))
                gc += 1
    return gc
