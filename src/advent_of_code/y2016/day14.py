import re
from hashlib import md5
from itertools import count
from collections import deque

def hash(i, stretch):
    res = md5(salt + str(i).encode())
    for _ in range(stretch):
        res = md5(res.hexdigest().encode())
    return res.hexdigest()

def solve(stretch=0):
    pattern = re.compile(r"(.)\1\1")
    q = deque(hash(i, stretch) for i in range(1002))
    keys = 0
    for i in count():
        match = pattern.search(q.popleft())
        if match:
            digits = match[1] * 5
            if any(digits in h for h in q):
                keys += 1
                if keys == 64:
                    return i
        q.append(hash(i + 1002, stretch))

salt = open("resources/inputs/2016/day14.txt").read().strip().encode()
print(solve())
print(solve(2016))
