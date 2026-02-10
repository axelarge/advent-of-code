def run(raw_input):
    inp = iter(str(raw_input))
    w, x, y, z = 0, 0, 0, 0
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 1
    x += 14
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 12
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 1
    x += 10
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 9
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 1
    x += 13
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 8
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 26
    x += -8
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 3
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 1
    x += 11
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 0
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 1
    x += 11
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 11
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 1
    x += 14
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 10
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 26
    x += -11
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 13
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 1
    x += 14
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 3
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 26
    x += -1
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 10
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 26
    x += -8
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 10
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 26
    x += -5
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 14
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 26
    x += -16
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 6
    y *= x
    z += y
    w = int(next(inp))
    x *= 0
    x += z
    x %= 26
    z //= 26
    x += -6
    x = int(x == w)
    x = int(x == 0)
    y *= 0
    y += 25
    y *= x
    y += 1
    z *= y
    y *= 0
    y += w
    y += 5
    y *= x
    z += y
    return w, x, y, z


#
best = 0
best_r = None
#12345678901234
#11111111111111
#99999999999999
# for i in reversed(range(11111111111111, 99999999999999+1)):
for i in reversed(range(11111111111111, 99998724000000+1)):
    if i % 1000000 == 0:
        print(f"...{i}")
    if "0" in str(i):
        continue
    ret = run(i)
    w, x, y, z = ret
    if z == 0:
        if i > best:
            best = i
            best_r = ret
            break

print(best_r)
print(best)
