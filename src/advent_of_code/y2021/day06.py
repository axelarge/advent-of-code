from collections import Counter

IN = list(map(int, open("resources/inputs/2021/day06.txt").read().split(",")))

def solve(seed, n):
    fishes = Counter(seed)
    for i in range(n):
        new = Counter()
        for fish, count in fishes.items():
            fish -= 1
            if fish < 0:
                fish = 6
                new[8] += count
            new[fish] += count
        fishes = new
    return sum(fishes.values())

print(solve(IN, 80))
print(solve(IN, 256))
