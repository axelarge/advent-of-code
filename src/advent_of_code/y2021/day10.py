COST = dict(zip("([{<)]}>", [1, 2, 3, 4, 3, 57, 1197, 25137]))
D = {"(": ")", "<": ">", "[": "]", "{": "}"}
R = {v: k for k, v in D.items()}

invalid = 0
scores = []
for line in open("resources/inputs/2021/day10.txt").read().splitlines():
    stack = []
    is_valid = True
    for ch in line:
        if ch in D:
            stack.append(ch)
        elif R[ch] == stack[-1]:
            stack.pop()
        else:
            is_valid = False
            invalid += COST[ch]
            break
    if is_valid:
        score = 0
        for ch in stack[::-1]:
            score = score * 5 + COST[ch]
        scores.append(score)

print(invalid)
print(sorted(scores)[len(scores) // 2])
