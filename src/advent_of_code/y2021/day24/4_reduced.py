def run(raw_input):
    raw = raw_input
    y = 0

    # digit 0
    w = raw[0]
    # z //= 1
    # x = int(w != z % 26 + 14)
    # x = 1
    # z = z * (1 + 25 * x) + (w + 12) * x
    # z = z * (1 + 25) + (w + 12)
    z = (w + 12)

    # digit 1
    w = raw[1]
    # z //= 1
    # x = int(w != z % 26 + 10)
    # x = 1
    # z *= 25 * x + 1
    z *= 26
    z += (w + 9)

    # digit 2
    w = raw[2]
    # z //= 1
    # x = int(w != z % 26 + 13)
    # x = 1
    # z *= 25 * x + 1
    z *= 26
    z += (w + 8)

    # digit 3
    w = raw[3]
    z //= 26
    # x = int(w != z % 26 - 8)
    # x = 1
    # z *= 25 * x + 1
    z *= 26
    # z += (w + 3) * x
    z += (w + 3)

    # digit 4
    w = raw[4]
    # z //= 1
    # x = int(w != z % 26 + 11)
    # x = 1
    # z *= 25 * x + 1
    z *= 26
    # z += w * x
    z += w

    # digit 5
    # w = int(next(inp))
    w = raw[5]
    # z //= 1
    # x = int(w != z % 26 + 11)
    x = 1
    # z *= 25 * x + 1
    z *= 26
    # z += (w + 11) * x
    z += (w + 11)

    # digit 6
    w = raw[6]
    # z //= 1
    # x = int(w != z % 26 + 14)
    x = 1
    # z *= 25 * x + 1
    z *= 26
    # z += (w + 10) * x
    z += (w + 10)

    # digit 7
    w = raw[7]
    z //= 26
    # x = int(w != z % 26 - 11)
    x = 1
    # z *= (25 * x) + 1
    z *= 26
    z += (w + 13)
    # z += (w + 13) * x

    # digit 8
    w = raw[8]
    # x = int(w != (z % 26) + 14)
    x = 1
    # z //= 1
    # z *= 25 * x + 1
    z *= 26
    # z += (w + 3) * x
    z += (w + 3)

    # digit 9
    w = raw[9]
    # x = 1
    # x = int(w != z % 26 - 1)
    z //= 26
    # z *= (25 * x) + 1
    z *= 26
    z += (w + 10)
    # z += (w + 10) * x

    # digit 10
    w = raw[10]
    # x = int(w != z % 26 - 8)
    # z //= 26
    # z *= 25 * x + 1
    # z += (w + 10) * x
    z *= 26
    z += w + 10

    # digit 11
    w = raw[11]
    # x = int(w != z % 26 - 5)
    # x = 1
    z //= 26
    # z *= 25 * x + 1
    # z += (w + 14) * x
    z *= 26
    z += (w + 14)

    # digit 12
    w = raw[12]
    # x = int(w != z % 26 - 16)
    z //= 26
    # z *= 25 * x + 1
    # z += (w + 6) * x
    z *= 26
    z += (w + 6)
    #

    # digit 13
    w = raw[13]
    # x = int(w != z % 26 - 6)
    # w == z % 26 - 6
    # z < 26
    z //= 26
    # z *= 25 * x + 1
    z *= 26
    z += (w + 5)  # w== 0

    return w, x, y, z

# 01234567890123
#              0
#
best = 0
best_r = None
for i in range(int("9" * 13) + 1, int("9" * 14) + 1):
    if i % 1000 == 0:
        print(f"...{i}")
    si = str(i)
    if "0" in si:
        continue
    ret = run(si)
    w, x, y, z = ret
    if z == 0:
        if i > best:
            best = i
            best_r = ret

print(best_r)
print(best)
