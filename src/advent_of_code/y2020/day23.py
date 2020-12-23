def nexts(after, x, n):
    while n:
        n -= 1
        x = after[x]
        yield x


def play(seed, moves, n=None):
    n = n or len(seed)
    after = list(range(1, n + 2))
    for i in range(len(seed)):
        after[seed[i - 1]] = seed[i]
    if n > len(seed):
        after[seed[-1]] = len(seed) + 1
        after[-1] = seed[0]

    cur = seed[0]
    for _ in range(moves):
        a, b, c = nexts(after, cur, 3)
        after[cur] = after[c]

        dest = cur - 1
        while dest < 1 or dest in (a, b, c):
            if dest <= 1:
                dest = n
            else:
                dest -= 1

        after[c] = after[dest]
        after[dest] = a
        cur = after[cur]
    return after


cups = list(map(int, open("resources/inputs/2020/day23.txt").read().strip()))
print("".join(map(str, nexts(play(cups, 100), 1, len(cups) - 1))))
x, y = nexts(play(cups, 10_000_000, 1_000_000), 1, 2)
print(x * y)
