#!/usr/local/bin/python3
from itertools import groupby, islice


def to_i(c):
    return ord(c) - 97


def from_s(s):
    return list(map(to_i, reversed(s)))


def to_s(ii):
    return "".join(chr(i + 97) for i in reversed(ii))


BAD_CHARS = from_s("iol")
Z = to_i("z")


def valid(pw):
    return (all(c not in BAD_CHARS for c in pw)
            and any(a + 2 == b + 1 == c for c, b, a in zip(pw, pw[1:], pw[2:]))
            and len({k for k, g in groupby(pw) if len(tuple(g)) > 1}) > 1)


def inc_pw(pw):
    res = []
    carry = 1
    for c in pw:
        carry, nc = divmod(c + carry, Z + 1)
        if nc in BAD_CHARS:
            nc += 1
            res = [0] * len(res)
        res.append(nc)
    if carry:
        res.append(0)  # append a
    return res


def iterate(f, x):
    while True:
        yield x
        x = f(x)


def next_pw(pw):
    return next(filter(valid, iterate(inc_pw, inc_pw(pw))))


for good_pw in map(to_s, islice(iterate(next_pw, from_s("hepxcrrq")), 3)):
    print(good_pw)
