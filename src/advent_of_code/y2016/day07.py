import re

def abba(s):
    return bool(re.search(r"(.)((?!\1).)\2\1", s) and not re.search(r"\[[^]]*([^]])((?!\1)[^]])\2\1[^]]*\]", s))

def sls(s):
    insides = re.findall(r"\[([^]]+)\]", s)
    outsides = re.findall(r"(?:^|\])([^[]+)(?:\[|$)", s)
    for inside in insides:
        for i in range(len(inside) - 2):
            x, y, z = inside[i:i + 3]
            if x == z != y and any(y + x + y in outside for outside in outsides):
                return True
    return False

data = open("resources/inputs/2016/day07.txt").read().splitlines()
print(sum(map(abba, data)))
print(sum(map(sls, data)))
