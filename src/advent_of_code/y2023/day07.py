from collections import Counter

F = open("resources/inputs/2023/day07.txt").read().splitlines()


def score(hand, jokers):
    vs = Counter(Counter(hand).values())
    vs[0] = 0
    if max(vs) + jokers == 5:
        return 7
    if max(vs) + jokers == 4:
        return 6
    if 3 in vs and 2 in vs or vs[2] + jokers == 3:
        return 5
    if max(vs) + jokers == 3:
        return 4
    if vs[2] + jokers == 2:
        return 3
    if max(vs) + jokers == 2:
        return 2
    return 1


hands = []
for line in F:
    hand, bid = line.split()
    hands.append((int(bid),
                  [score(hand, 0), *map("23456789TJQKA".index, hand)],
                  [score(hand.replace("J", ""), hand.count("J")), *map("J23456789TQKA".index, hand)]))

res1 = sum(rank * bid for rank, (bid, *_) in enumerate(sorted(hands, key=lambda h: h[1]), 1))
res2 = sum(rank * bid for rank, (bid, *_) in enumerate(sorted(hands, key=lambda h: h[2]), 1))
print(res1)
print(res2)
assert res1 == 250370104
assert res2 == 251735672
