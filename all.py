import sys
from io import StringIO
from time import time
from datetime import date
import argparse

today = date.today()

parser = argparse.ArgumentParser(description="Run puzzle solutions")
parser.add_argument("year", type=int, metavar="year", nargs="?", choices=range(2015, today.year + 1), default=today.year,
                    help=f"2015..{today.year}")
parser.add_argument("day", type=int, metavar="day", nargs="?", choices=range(1, 26), help="1..25")
args = parser.parse_args()


def run_one(year, day):
    try:
        sio = StringIO()
        sys.stdout = sio
        start = time()
        __import__(f"src.advent_of_code.y{year}.day{day:02}")
        elapsed = time() - start
        return sio.getvalue(), elapsed
    finally:
        sys.stdout = sys.__stdout__


if args.day:
    try:
        print(f"Year {args.year} day {args.day}        ...", end="", flush=True)
        res, elapsed = run_one(args.year, args.day)
        print(f"\rYear {args.year} day {args.day}      {elapsed * 1000:.2f}ms")
        print(res)
    except Exception as e:
        print(f"\rYear {args.year} day {args.day}:     error")
        print(f"{e}")
        print()
else:
    total = 0
    print(f"======== {args.year} ========")
    for day in range(1, 26):
        try:
            res, elapsed = run_one(args.year, day)
            total += elapsed
            print(f"Day {day:02}      {elapsed * 1000:8.2f}ms")
            print(res)
        except Exception as e:
            print(f"Day {day:02}           error")
            print(f"{e}")
            print()
    print(f"----------------------")
    print(f"Total       {total * 1000:8.2f}ms")
