F = open("resources/inputs/2022/day01.txt")
elves = sorted(sum(map(int, chunk.splitlines())) for chunk in F.read().split("\n\n"))

print(elves[-1])
print(sum(elves[-3:]))
