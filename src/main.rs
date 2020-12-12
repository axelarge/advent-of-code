mod advent_of_code;

use std::env;
use crate::advent_of_code::support::{timed, get_stdin, get_input};


fn main() -> Result<(), String> {
    let args = env::args().collect::<Vec<_>>();
    let year: u32 = args.get(1).ok_or("Missing year")?.parse().or(Err("Invalid year."))?;
    let day: u32 = args.get(2).ok_or("Missing day")?.parse().or(Err("Invalid day"))?;

    let solve: fn(String) = get_solution(year, day)
        .ok_or(format!("No solution found for year {} day {}.", year, day))?;

    let input = if args.get(3).map_or(false, |a| { a == "-" }) {
        get_stdin()?
    } else {
        get_input(year, day)?
    };

    timed(&format!("Year {} day {}", year, day), || { solve(input) });

    Ok(())
}

fn get_solution(year: u32, day: u32) -> Option<fn(String)> {
    Option::from(match (year, day) {
        (2020, 10) => advent_of_code::y2020::day10::run,
        (2020, 12) => advent_of_code::y2020::day12::run,
        _ => return None
    })
}
