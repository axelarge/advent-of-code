def run(raw_input):
    raw = raw_input
    y = 0

    # digit 0
    w = raw[0]
    z = (w + 12)

    # digit 1
    w = raw[1]
    z *= 26
    z += (w + 9)

    # digit 2
    w = raw[2]
    z *= 26
    z += (w + 8)

    # digit 3
    w = raw[3]
    z //= 26
    z *= 26
    z += (w + 3)

    # digit 4
    w = raw[4]
    z *= 26
    z += w

    # digit 5
    w = raw[5]
    z *= 26
    z += (w + 11)

    # digit 6
    w = raw[6]
    z *= 26
    z += (w + 10)

    # digit 7
    w = raw[7]
    z //= 26
    z *= 26
    z += (w + 13)

    # digit 8
    w = raw[8]
    z *= 26
    z += (w + 3)

    # digit 9
    w = raw[9]
    z //= 26
    z *= 26
    z += (w + 10)

    # digit 10
    w = raw[10]
    z *= 26
    z += w + 10

    # digit 11
    w = raw[11]
    z //= 26
    z *= 26
    z += (w + 14)

    # digit 12 # any == 9
    w = raw[12]
    z //= 26
    z *= 26
    z += (w + 6)

    # z < 26

    # digit 13 # == 0
    w = raw[13]
    z //= 26
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
