def run(raw_input):
    raw = list(map(int, str(raw_input)))
    y = 0
    z = 0

    # digit 0
    w = raw[0]
    # z //= 1
    x = int(w != z % 26 + 14)
    z = z * (1 + 25 * x) + (w + 12) * x

    # digit 1
    w = raw[1]
    # z //= 1
    x = int(w != z % 26 + 10)
    z *= 25 * x + 1
    z += (w + 9) * x

    # digit 2
    w = raw[2]
    # z //= 1
    x = int(w != z % 26 + 13)
    z *= 25 * x + 1
    z += (w + 8) * x

    # digit 3
    w = raw[3]
    z //= 26
    x = int(w != z % 26 - 8)
    z *= 25 * x + 1
    z += (w + 3) * x

    # digit 4
    w = raw[4]
    # z //= 1
    x = int(w != z % 26 + 11)
    z *= 25 * x + 1
    z += w * x

    # digit 5
    # w = int(next(inp))
    w = raw[5]
    # z //= 1
    x = int(w != z % 26 + 11)
    z *= 25 * x + 1
    z += (w + 11) * x

    # digit 6
    w = raw[6]
    # z //= 1
    x = int(w != z % 26 + 14)
    z *= 25 * x + 1
    z += (w + 10) * x

    # digit 7
    w = raw[7]
    z //= 26
    x = int(w != z % 26 - 11)
    z *= (25 * x) + 1
    z += (w + 13) * x

    # digit 8
    w = raw[8]
    x = int(w != (z % 26) + 14)
    # z //= 1
    z *= 25 * x + 1
    z += (w + 3) * x

    # digit 9
    w = raw[9]
    x = int(w != z % 26 - 1)
    z //= 26
    z *= (25 * x) + 1
    z += (w + 10) * x

    # digit 10
    w = raw[10]
    x = int(w != z % 26 - 8)
    z //= 26
    z *= 25 * x + 1
    z += (w + 10) * x

    # digit 11
    w = raw[11]
    x = int(w != z % 26 - 5)
    z //= 26
    z *= 25 * x + 1
    z += (w + 14) * x

    # digit 12
    w = raw[12]
    x = int(w != z % 26 - 16)
    z //= 26
    z *= 25 * x + 1
    z += (w + 6) * x

    # digit 13
    w = raw[13]
    x = int(w != z % 26 - 6)
    z //= 26
    z *= 25 * x + 1
    z += (w + 5) * x

    return w, x, y, z


#
best = 0
best_r = None
import random
def rr():
    while True:
        yield random.randint(11111111111111, 99999999999999 + 1)
# for i in range(11111111111111, 99999999999999 + 1):
for i in rr():
    if i % 10000000 == 0:
        print(f"...{i}")
    if "0" in str(i):
        continue
    ret = run(i)
    w, x, y, z = ret
    if z == 0:
        print("VALID", i)
        # if i > best:
        #     best = i
        #     best_r = ret

# print(best_r)
# print(best)
