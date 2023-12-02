#!/usr/local/bin/python3
import argparse
import datetime
import subprocess
from pathlib import Path

import requests

current_year = datetime.date.today().year
parser = argparse.ArgumentParser(description="Create solution from template and fetch input")
parser.add_argument("year", type=int, metavar="year", choices=range(2015, current_year + 1), help=f"2015..{current_year}")
parser.add_argument("day", type=int, metavar="day", choices=range(1, 25 + 1), help="1..25")
parser.add_argument("lang", choices=["clj", "py", "none"])
parser.add_argument("--dry", action="store_true", help="Don't fetch input")
args = parser.parse_args()


def replace(s):
    return s.format(YYYY=f"{args.year}", DD=f"{args.day:02d}", D=f"{args.day}")


def print_skip(path):
    print(f"File already exists, skipping ({path})")


def git_add(path):
    subprocess.run(["git", "add", path], check=True)


def copy_template(template, to_file):
    out_path = Path(replace(to_file))
    with open(f"resources/templates/{template}") as tpl:
        try:
            out_path.parent.mkdir(exist_ok=True)
            with out_path.open("x") as out:
                out.write(replace(tpl.read()))
        except FileExistsError:
            print_skip(out_path)
    git_add(out_path)


if args.lang == "py":
    copy_template("code.py.txt", "src/advent_of_code/y{YYYY}/day{DD}.py")
elif args.lang == "clj":
    copy_template("code.clj.txt", "src/advent_of_code/y{YYYY}/day{DD}.clj")
    copy_template("test.clj.txt", "test/advent_of_code/y{YYYY}/day{DD}_test.clj")

if not args.dry:
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
    git_add(input_file)
