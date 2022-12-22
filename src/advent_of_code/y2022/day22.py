import re

F = open("resources/inputs/2022/day22.txt").read()
raw_map, raw_moves = F.split("\n\n")
G = {(x, y): c for y, row in enumerate(raw_map.split("\n")) for x, c in enumerate(row) if c != " "}
moves = re.findall(r"\d+|L|R", raw_moves)
# Does not work on test input because it has a different shape
S = 50
W = 3 * S
H = 4 * S


def rotate(current, side):
    x, y = current
    if side == "R":
        return -y, x
    else:
        return y, -x


def step(pos, delta, part):
    x, y = pos
    dx, dy = delta
    x, y = x + dx, y + dy
    if part == 1:
        while (x, y) not in G:
            x = (x + dx) % W
            y = (y + dy) % H
    else:
        if (x, y) not in G:
            xx, yy = x % S, y % S
            match pos[0] // S, pos[1] // S, delta:
                case 1, 0, (-1, 0):  # A, left to D left
                    return (0, S * 3 - 1 - yy), (1, 0)
                case 1, 0, (0, -1):  # A, up to F left
                    return (0, S * 3 + xx), (1, 0)
                case 2, 0, (0, -1):  # B, up to F down
                    return (xx, S * 4 - 1), (0, -1)
                case 2, 0, (0, 1):  # B down into C right
                    return (S * 2 - 1, S + xx), (-1, 0)
                case 2, 0, (1, 0):  # B, right to E right
                    return (S * 2 - 1, S * 3 - 1 - yy), (-1, 0)
                case 1, 1, (-1, 0):  # C, left to D top
                    return (yy, S * 2), (0, 1)
                case 1, 1, (1, 0):  # C, right to B bottom
                    return (S * 2 + yy, S - 1), (0, -1)
                case 0, 2, (-1, 0):  # D , left to A left
                    return (S, S - 1 - yy), (1, 0)
                case 0, 2, (0, -1):  # D up into C left
                    return (S, S + xx), (1, 0)
                case 1, 2, (1, 0):  # E, right to B right
                    return (S * 3 - 1, S - 1 - yy), (-1, 0)
                case 1, 2, (0, 1):  # E down into F right
                    return (S - 1, S * 3 + xx), (-1, 0)
                case 0, 3, (-1, 0):  # F left into A top
                    return (S + yy, 0), (0, 1)
                case 0, 3, (1, 0):  # F right into E bottom
                    return (S + yy, S * 3 - 1), (0, -1)
                case 0, 3, (0, 1):  # F down into B top
                    return (S * 2 + xx, 0), (0, 1)
            assert False, f"Bad case {pos} {delta}"
    return (x, y), (dx, dy)


def solve(part):
    delta = (1, 0)
    pos = (0, 0)
    while pos not in G:
        pos = (pos[0] + 1, 0)

    for move in moves:
        if move.isalpha():
            delta = rotate(delta, move)
        else:
            move = int(move)
            for _ in range(move):
                pos1, dir1 = step(pos, delta, part)
                assert pos1 in G, f"Bad new position {pos1}"
                if G[pos1] == ".":
                    pos = pos1
                    delta = dir1
                else:
                    break
    x, y = pos
    return 1000 * (y + 1) + 4 * (x + 1) + [(1, 0), (0, 1), (-1, 0), (0, -1)].index(delta)


part1 = solve(part=1)
print(part1)
assert part1 == 122082

part2 = solve(part=2)
print(part2)
assert part2 == 134076
