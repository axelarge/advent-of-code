use crate::advent_of_code::support::*;
use std::convert::TryFrom;

fn solve(commands: &Vec<(char, i32)>, mut dxy: Point, part: Part) -> i32 {
    let mut xy = Point::ZERO;
    for &(c, n) in commands {
        match c {
            'F' => xy += dxy * n,
            'R' => dxy = dxy.rotate(n / 90),
            'L' => dxy = dxy.rotate(-n / 90),
            _ => {
                let delta = Point::from(Direction::try_from(c).unwrap());
                match part {
                    Part::One => xy += delta * n,
                    Part::Two => dxy += delta * n,
                }
            }
        }
    }
    xy.manhattan()
}

fn parse_command(l: &str) -> (char, i32) {
    (l.chars().next().unwrap(), l[1..].parse().unwrap())
}

pub fn run(input: String) {
    let commands: Vec<(char, i32)> = input.lines().map(parse_command).collect();
    println!("{:?}", solve(&commands, point(1, 0), Part::One));
    println!("{:?}", solve(&commands, point(10, -1), Part::Two));
}
