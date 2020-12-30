def size(s: str, part2=False):
    tot = 0
    upto = len(s)
    repeat = 1
    i = 0
    stack = []
    while i < len(s):
        if s[i] == "(" and (part2 or not stack):
            stack.append((upto, repeat))
            j = s.index(")", i)
            n, x = map(int, s[i + 1:j].split("x"))
            upto = j + n
            repeat *= x
            i = j + 1
        else:
            tot += repeat
            i += 1
            while i > upto:
                upto, repeat = stack.pop()
    return tot


data = open("resources/inputs/2016/day09.txt").read().strip()
print(size(data, False))
print(size(data, True))
