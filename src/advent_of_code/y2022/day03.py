F = open("resources/inputs/2022/day03.txt").read().splitlines()


def score(x: str):
    return ord(x.lower()) - ord("a") + 1 + 26 * x.isupper()


tot = 0
for elf in F:
    l = len(elf) // 2
    tot += score((set(elf[:l]) & set(elf[l:])).pop())
print(tot)
assert tot == 8123

tot = 0
for group in zip(*[iter(F)] * 3):
    tot += score(set.intersection(*map(set, group)).pop())
print(tot)
assert tot == 2620
