import re
from itertools import permutations

def swap_pos(val, x, y):
    val[x], val[y] = val[y], val[x]

def swap_letter(val, x, y):
    swap_pos(val, val.index(x), val.index(y))

def rotate(val, x):
    x %= len(val)
    return val[-x:] + val[:-x]

def rotate_based(val, x):
    idx = val.index(x)
    return rotate(val, idx + 1 + (idx >= 4))

def reverse(val, x, y):
    val[x:y + 1] = val[x:y + 1][::-1]

def move(val, x, y):
    val.insert(y, val.pop(x))

steps = []
ints = lambda g: list(map(int, g))
for line in open("resources/inputs/2016/day21.txt"):
    for pattern, f, argf in [
        [r"swap position (.) with position (.)", swap_pos, ints],
        [r"swap letter (.) with letter (.)", swap_letter, list],
        [r"rotate (left|right) (.) steps?", rotate, lambda g: [int(g[1]) * [1, -1][g[0] == "left"]]],
        [r"rotate based on position of letter (.)", rotate_based, list],
        [r"reverse positions (.) through (.)", reverse, ints],
        [r"move position (.) to position (.)", move, ints],
    ]:
        match = re.match(pattern, line)
        if match:
            steps.append((f, argf(match.groups())))
            break

def scramble(val):
    val = list(val)
    for f, args in steps:
        val = f(val, *args) or val
    return "".join(val)

print(scramble("abcdefgh"))
print(next("".join(val) for val in permutations("fbgdceah") if scramble(val) == "fbgdceah"))
