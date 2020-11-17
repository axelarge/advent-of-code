#!/usr/local/bin/python3
from sys import argv

assert len(argv) == 3 and all(a.isnumeric() for a in argv[1:]), f"Usage: {argv[0]} 2020 24"

y = f"{int(argv[1])}"
d = f"{int(argv[2]):02d}"


def replace(s):
    return s.replace("{YYYY}", y).replace("{DD}", d)


def copy_template(template, to_file):
    with open(f"resources/templates/{template}") as tpl:
        with open(replace(to_file), "x") as out:
            out.write(replace(tpl.read()))


copy_template("code.clj.txt", "src/advent_of_code/{YYYY}/day{DD}.clj")
copy_template("test.clj.txt", "test/advent_of_code/{YYYY}/day{DD}_test.clj")
