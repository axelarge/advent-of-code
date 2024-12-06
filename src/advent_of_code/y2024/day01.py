from collections import Counter

F = open("resources/inputs/2024/day01.txt").read().splitlines()
left, right = list(zip(*[map(int, line.split()) for line in F]))
print(sum(abs(l - r) for l, r in zip(sorted(left), sorted(right))))
c = Counter(right)
print(sum(x * c[x] for x in left))
