from itertools import islice

MOVES = open("resources/inputs/2022/day17.txt").read().strip()
RAW_TILES = """\
..####.

...#...
..###..
...#...

....#..
....#..
..###..

..#....
..#....
..#....
..#....

..##...
..##..."""
TILES = [[int(line.replace(".", "0").replace("#", "1"), 2)
          for line in tile.splitlines()][::-1]
         for tile in RAW_TILES.split("\n\n")]


def debug(screen, prefix=""):
    start = True
    for line in reversed(screen):
        if start and not line:
            continue
        start = False
        print(prefix + bin(line)[2:].rjust(7, ".").replace("0", ".").replace("1", "#"))
    print()


def collision(screen, tile, y):
    return any(screen[y + i] & t for i, t in enumerate(tile))


def fingerprint(screen):
    res = 0
    for line in islice(filter(None, reversed(screen)), 10):
        res = (res << 7) | res | line
    return res


def solve(rounds):
    screen = [~0]
    top = 0
    skipped = 0
    seen = {}
    i = 0
    move_idx = 0
    while i < rounds:
        tile_idx = i % len(TILES)
        tile = TILES[tile_idx]

        while len(screen) < top + 4 + len(tile):
            screen.append(0)
        tile_y = top + 4

        while True:
            move = MOVES[move_idx]
            move_idx = (move_idx + 1) % len(MOVES)
            if move == "<":
                if not any(t & 1 << 6 for t in tile):
                    tile1 = [t << 1 for t in tile]
                    if not collision(screen, tile1, tile_y):
                        tile = tile1
            else:
                if not any(t & 1 for t in tile):
                    tile1 = [t >> 1 for t in tile]
                    if not collision(screen, tile1, tile_y):
                        tile = tile1

            if collision(screen, tile, tile_y - 1):
                break
            else:
                tile_y -= 1

        for ti, t in enumerate(tile):
            screen[tile_y + ti] |= t
        top = max(top, tile_y + len(tile) - 1)

        sig = (tile_idx, move_idx, fingerprint(screen))
        if prev := seen.get(sig):
            cycle = i - prev[0]
            n = (rounds - i) // cycle
            if n > 0:
                i += n * cycle
                skipped += n * (top - prev[1])
        else:
            seen[sig] = (i, top)
        i += 1

    # debug(screen[-20:])
    return top + skipped


part1 = solve(2022)
print(part1)
assert part1 == 3202

part2 = solve(1000000000000)
print(part2)
assert part2 == 1591977077352
