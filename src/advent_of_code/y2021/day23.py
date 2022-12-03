import re
from functools import lru_cache

TARGET_X = dict(A=2, B=4, C=6, D=8)
COST = dict(A=1, B=10, C=100, D=1000)
HALL_X = [0, 1, 3, 5, 7, 9, 10]

IN = open("resources/inputs/2021/day23.txt").read()
a1, b1, c1, d1, a2, b2, c2, d2 = re.findall(r"[A-D]", IN)


def room_to_hallway(hall, x, ry, letter):
    for path in (x1 for x1 in HALL_X if x1 > x), \
                (x1 for x1 in reversed(HALL_X) if x1 < x):
        for x1 in path:
            if hall[x1]:
                break
            yield x1, (ry + 1 + abs(x - x1)) * COST[letter]


def upd(tup, idx, val):
    new = list(tup)
    new[idx] = val
    return tuple(new)


@lru_cache(None)
def solve(hall, rooms):
    if all(all(spot == letter for spot in room) for room, letter in zip(rooms, "ABCD")):
        return 0

    # hallway -> room
    for x, letter in enumerate(hall):
        if letter:
            tx = TARGET_X[letter]
            rx = "ABCD".index(letter)
            ry = len(rooms[rx]) - 1
            while rooms[rx][ry] == letter and ry > 0:
                ry -= 1
            if not (rooms[rx][ry] or any(hall[x1] for x1 in range(min(x, tx) + 1, max(x, tx)))):
                cost = (ry + 1 + abs(x - tx)) * COST[letter]
                return cost + solve(upd(hall, x, None), upd(rooms, rx, upd(rooms[rx], ry, letter)))

    # room -> hallway
    best = 1 << 31
    for rx, room in enumerate(rooms):
        should = "ABCD"[rx]
        for ry, letter in enumerate(room):
            if letter and not any(room[:ry]) and any(spot != should for spot in room[ry:]):
                for x1, cost in room_to_hallway(hall, (rx + 1) * 2, ry, letter):
                    best = min(best, cost + solve(upd(hall, x1, letter), upd(rooms, rx, upd(rooms[rx], ry, None))))
                break
    return best


print(solve((None,) * (max(HALL_X) + 1), ((a1, a2), (b1, b2), (c1, c2), (d1, d2))))
print(solve((None,) * (max(HALL_X) + 1),
            ((a1, "D", "D", a2), (b1, "C", "B", b2), (c1, "B", "A", c2), (d1, "A", "C", d2))))
