from collections import deque


def play(p1, p2, recursive=False):
    p1 = deque(p1)
    p2 = deque(p2)
    seen = set()
    while p1 and p2:
        key = (tuple(p1), tuple(p2))
        if key in seen:
            return p1, True
        seen.add(key)

        card1 = p1.popleft()
        card2 = p2.popleft()
        if recursive and len(p1) >= card1 and len(p2) >= card2:
            p1won = play(list(p1)[:card1], list(p2)[:card2], True)[1]
        else:
            p1won = card1 > card2

        if p1won:
            p1.append(card1)
            p1.append(card2)
        else:
            p2.append(card2)
            p2.append(card1)

    return (p1, True) if p1 else (p2, False)


def score(deck):
    return sum(i * x for i, x in enumerate(reversed(deck), 1))


IN = open("resources/inputs/2020/day22.txt").read()
p1, p2 = [list(map(int, s.splitlines()[1:])) for s in IN.split("\n\n")]

print(score(play(p1, p2)[0]))
print(score(play(p1, p2, True)[0]))
