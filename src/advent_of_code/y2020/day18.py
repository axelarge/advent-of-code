import re
from operator import add, mul

OPS = {"+": add, "*": mul}
data = open("resources/inputs/2020/day18.txt").read().splitlines()

def eval_str(s, precedence):
    out = []
    stack = []

    def reduce1():
        op = OPS.get(stack.pop())
        out.append(op(out.pop(-2), out.pop()))

    for token in re.findall(r"\d+|[+*()]", s):
        if token in OPS:
            while stack and precedence.get(stack[-1], -1) >= precedence[token]:
                reduce1()
            stack.append(token)
        elif token == "(":
            stack.append(token)
        elif token == ")":
            while stack[-1] != "(":
                reduce1()
            stack.pop()
        else:
            out.append(int(token))

    while stack:
        reduce1()

    return out.pop()

print(sum(eval_str(line, {"+": 0, "*": 0}) for line in data))
print(sum(eval_str(line, {"+": 1, "*": 0}) for line in data))
