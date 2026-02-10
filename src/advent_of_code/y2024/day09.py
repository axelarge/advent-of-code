def checksum(disk):
    return sum(i * int(c) for i, c in enumerate(disk) if c != -1)


def solve1(disk):
    disk = disk.copy()
    l = 0
    r = len(disk) - 1
    while l < r:
        if disk[l] != -1:
            l += 1
            continue
        if disk[r] == -1:
            r -= 1
            continue
        disk[l], disk[r] = disk[r], disk[l]
    return disk


def solve2(disk):
    disk = disk.copy()
    r = len(disk) - 1
    skip = {-1}
    while r >= 0:
        if disk[r] in skip:
            r -= 1
            continue
        n = 1
        while disk[r - 1] == disk[r]:
            r -= 1
            n += 1
        l = 0
        try:
            while any(c != -1 for c in disk[l:l + n]):
                l = disk.index(-1, l + 1, r)
        except ValueError:
            r -= 1
            continue
        disk[l:l + n], disk[r:r + n] = disk[r:r + n], disk[l:l + n]
        skip.add(disk[l])
    return disk


F = open("resources/inputs/2024/day09.txt").read().strip()
disk = []
for i, c in enumerate(F):
    disk.extend([-1 if i % 2 == 1 else i // 2] * int(c))

print(checksum(solve1(disk)))
print(checksum(solve2(disk)))
