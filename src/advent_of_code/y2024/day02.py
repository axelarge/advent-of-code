def safe(r, dampen=False):
    # The levels are either all increasing or all decreasing.
    # Any two adjacent levels differ by at least one and at most three.
    sign = None
    for i, (a, b) in enumerate(zip(r, r[1:])):
        d = a - b
        s = d // abs(d) if d else 0
        if sign is None:
            sign = s
        if abs(d) < 1 or abs(d) > 3 or sign != s:
            if dampen:
                for j in range(max(0, i - 1), min(i + 2, len(r))):
                    if safe(r[:j] + r[j + 1:]):
                        return True
            return False
    return True


F = open("resources/inputs/2024/day02.txt").read().splitlines()
reports = [list(map(int, line.split())) for line in F]
print(sum(safe(report) for report in reports))
print(sum(safe(report, True) for report in reports))
