mod advent_of_code;

use std::env;
use crate::advent_of_code::support::get_input;


fn main() -> Result<(), String> {
    let args = env::args().collect::<Vec<_>>();
    let year: u32 = args.get(1).ok_or("Missing year")?.parse().or(Err("Invalid year."))?;
    let day: u32 = args.get(2).ok_or("Missing day")?.parse().or(Err("Invalid day"))?;

    let solve = match (year, day) {
        (2020, 10) => Some(advent_of_code::y2020::day10::run),
        _ => None,
    }.ok_or(format!("No solution found for year {} day {}.", year, day))?;

    let input = get_input(year, day)?;

    solve(input);

    Ok(())
}
