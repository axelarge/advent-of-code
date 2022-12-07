from collections import defaultdict

F = open("resources/inputs/2022/day07.txt").read().splitlines()

cwd = []
sizes = defaultdict(int)
for line in F:
    if line.startswith("$"):
        _, command, *args = line.split(" ")
        if command == "cd":
            folder = args[0]
            if folder == "..":
                cwd.pop()
            else:
                cwd.append(folder)
        else:
            assert command == "ls", f"Unknown command {line}"
    elif line.startswith("dir"):
        pass
    else:
        size = int(line.split(" ")[0])
        for i in range(len(cwd)):
            sizes[tuple(cwd[:i + 1])] += size

part1 = sum(s for s in sizes.values() if s < 100000)
print(part1)
assert part1 == 1667443

used = sizes[("/",)]
part2 = min(s for s in sizes.values() if 70000000 - used + s >= 30000000)
print(part2)
assert part2 == 8998590
