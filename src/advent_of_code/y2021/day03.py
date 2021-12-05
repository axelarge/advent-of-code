from collections import Counter

IN = open("resources/inputs/2021/day03.txt").readlines()

cs = [Counter(line[i] for line in IN) for i in range(len(IN[0]))]
g = int("".join(c.most_common()[0][0] for c in cs), 2)
e = int("".join(c.most_common()[-1][0] for c in cs), 2)
print(g * e)


def find(lines, oxy):
    for i in range(len(lines[0])):
        c = Counter(line[i] for line in lines)
        if oxy:
            need = "01"[c["1"] >= c["0"]]
        else:
            need = "01"[c["1"] < c["0"]]
        lines = [line for line in lines if line[i] == need]
        if len(lines) == 1:
            return int(lines[0], 2)

print(find(IN, 1) * find(IN, 0))
