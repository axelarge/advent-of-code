import re


def ints(s):
    return list(map(int, re.findall(r"-?\d+", s)))


def solve(data, i=0):
    n_children = data[i]
    n_meta = data[i + 1]
    i += 2
    total = 0
    child_values = {}
    for child_idx in range(n_children):
        child_total, value, i = solve(data, i)
        total += child_total
        child_values[child_idx + 1] = value
    metas = data[i:i + n_meta]
    total += sum(metas)
    value = sum(child_values.get(m, 0) for m in metas) if child_values else total
    return total, value, i + n_meta


print(solve(ints(open("resources/inputs/2018/day08.txt").read()))[:2])
