use std::time::Instant;
use std::fs;

pub fn timed<F, R>(label: &str, f: F) -> R
    where F: FnOnce() -> R
{
    let start = Instant::now();
    let ret = f();
    println!("{} took {:.2?} to run", label, start.elapsed());
    ret
}

pub fn get_input(year: i32, day: i32) -> String {
    fs::read_to_string(format!("resources/inputs/{}/day{:02}.txt", year, day)).unwrap()
}
