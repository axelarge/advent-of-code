#!/usr/local/bin/python3
from sys import argv
from pathlib import Path
import requests

assert len(argv) == 3 and all(a.isnumeric() for a in argv[1:]), f"Usage: {argv[0]} 2020 24"

year = int(argv[1])
day = int(argv[2])

with open(".token") as f:
    TOKEN = f.read().strip()


def replace(s):
    return s.format(YYYY=f"{year}", DD=f"{day:02d}", D=f"{day}")


def copy_template(template, to_file):
    with open(f"resources/templates/{template}") as tpl:
        with open(replace(to_file), "x") as out:
            out.write(replace(tpl.read()))


Path(replace("src/advent_of_code/{YYYY}")).mkdir(exist_ok=True)
Path(replace("test/advent_of_code/{YYYY}")).mkdir(exist_ok=True)
Path(replace("resources/inputs/{YYYY}")).mkdir(exist_ok=True)


copy_template("code.clj.txt", "src/advent_of_code/{YYYY}/day{DD}.clj")
copy_template("test.clj.txt", "test/advent_of_code/{YYYY}/day{DD}_test.clj")


r = requests.get(replace("https://adventofcode.com/{YYYY}/day/{DD}/input"), cookies=dict(session=TOKEN))

with open(replace("resources/inputs/{YYYY}/day{DD}.txt"), "w") as inp:
    inp.write(r.text)
