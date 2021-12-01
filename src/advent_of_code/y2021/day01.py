lines = list(map(int, open("resources/inputs/2021/day01.txt")))

print(sum(1 for a, b in zip(lines, lines[1:]) if b > a))

sums = list(map(sum, zip(lines, lines[1:], lines[2:])))
print(sum(1 for a, b in zip(sums, sums[1:]) if b > a))
