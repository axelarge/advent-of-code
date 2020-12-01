#!/usr/local/bin/python3
from sys import argv
from pathlib import Path
import requests

assert len(argv) == 3 and all(a.isnumeric() for a in argv[1:]), f"Usage: {argv[0]} 2020 24"

year = int(argv[1])
day = int(argv[2])


def replace(s):
    return s.format(YYYY=f"{year}", DD=f"{day:02d}", D=f"{day}")


def print_skip(path):
    print(f"File already exists, skipping ({path})")


def copy_template(template, to_file):
    with open(f"resources/templates/{template}") as tpl:
        try:
            out_path = Path(replace(to_file))
            out_path.parent.mkdir(exist_ok=True)
            with out_path.open("x") as out:
                out.write(replace(tpl.read()))
        except FileExistsError:
            print_skip(out_path)


copy_template("code.clj.txt", "src/advent_of_code/{YYYY}/day{DD}.clj")
copy_template("test.clj.txt", "test/advent_of_code/{YYYY}/day{DD}_test.clj")

input_file = Path(replace("resources/inputs/{YYYY}/day{DD}.txt"))
if input_file.exists():
    print_skip(input_file)
else:
    url = replace("https://adventofcode.com/{YYYY}/day/{D}/input")
    token = Path(".token").read_text().strip()
    r = requests.get(url, cookies=dict(session=token))
    r.raise_for_status()
    input_file.parent.mkdir(exist_ok=True)
    input_file.write_text(r.text)
