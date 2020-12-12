use std::time::Instant;
use std::{fs, ops, io};
use std::convert::TryFrom;
use std::io::Read;

pub enum Part { One, Two }

pub fn timed<F, R>(label: &str, f: F) -> R
    where F: FnOnce() -> R
{
    let start = Instant::now();
    let ret = f();
    println!("{} took {:.2?} to run", label, start.elapsed());
    ret
}

pub fn get_stdin() -> Result<String, String> {
    let mut buf = String::new();
    io::stdin().read_to_string(&mut buf).or(Err("Could not read from stdin"))?;
    Ok(buf)
}

pub fn get_input(year: u32, day: u32) -> Result<String, String> {
    let path = format!("resources/inputs/{}/day{:02}.txt", year, day);
    fs::read_to_string(&path).or(Err(format!("Could not open file {}", &path)))
}


#[inline(always)]
pub fn modulo(x: i32, y: i32) -> i32 { (x % y + y) % y }


pub enum Direction { N, E, S, W }

impl TryFrom<char> for Direction {
    type Error = String;
    fn try_from(c: char) -> Result<Self, Self::Error> {
        Ok(match c {
            'N' => Direction::N,
            'E' => Direction::E,
            'S' => Direction::S,
            'W' => Direction::W,
            _ => return Err(format!("Can't create direction form {}", c))
        })
    }
}


#[derive(Copy, Clone)]
pub struct Point { x: i32, y: i32 }

pub const fn point(x: i32, y: i32) -> Point { Point { x, y } }

impl Point {
    pub const ZERO: Point = point(0, 0);

    pub fn rotate(self, turns: i32) -> Point {
        match modulo(turns, 4) {
            0 => self,
            1 => point(-self.y, self.x),
            2 => point(-self.x, -self.y),
            3 => point(self.y, -self.x),
            _ => unreachable!(),
        }
    }

    pub fn manhattan(self) -> i32 {
        self.x.abs() + self.y.abs()
    }
}

impl From<Direction> for Point {
    fn from(direction: Direction) -> Self {
        match direction {
            Direction::N => point(0, -1),
            Direction::E => point(1, 0),
            Direction::S => point(0, 1),
            Direction::W => point(-1, 0),
        }
    }
}

impl ops::Add<Point> for Point {
    type Output = Point;
    fn add(self, other: Point) -> Point {
        point(self.x + other.x, self.y + other.y)
    }
}

impl ops::AddAssign<Point> for Point {
    fn add_assign(&mut self, other: Point) {
        self.x += other.x;
        self.y += other.y;
    }
}

impl ops::Mul<i32> for Point {
    type Output = Point;
    fn mul(self, n: i32) -> Self::Output {
        point(self.x * n, self.y * n)
    }
}
