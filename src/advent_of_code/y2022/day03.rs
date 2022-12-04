use std::ops::BitAnd;

use crate::timed;


pub fn run(input: String) {
    timed("part 1", || println!("{}", part1(&input)));
    timed("part 2", || println!("{}", part2(&input)));
}

fn part1(input: &String) -> u32 {
    input.lines()
        .filter_map(|line| {
            let l = line.len() / 2;
            // let seen = bitset(&line[..l]);
            // line[l..].bytes().map(score).find(|s| seen.contains(*s))
            line[0..l].bytes().find(|c| line[l..].bytes().any(|lc| lc == *c))
        })
        .map(|x| score(x) as u32)
        .sum()
}

fn part2(input: &String) -> u32 {
    input.lines().array_chunks::<3>()
        .map(|[a, b, c]| {
            // let seen = bitset(a) & bitset(b) & bitset(c);
            // seen.bits.trailing_zeros()
            let seen = bitset(a) & bitset(b);
            c.bytes().map(score).find(|s| seen.contains(*s)).unwrap() as u32
        })
        .sum()
}

fn score(c: u8) -> u8 {
    (match c {
        b'a'..=b'z' => c - b'a' + 1,
        b'A'..=b'Z' => c - b'A' + 27,
        _ => panic!("Unexpected char {}", c),
    }).into()
}

#[inline]
fn bitset(s: &str) -> BitSet<u64> {
    s.bytes().map(score).collect()
}

struct BitSet<T> {
    bits: T,
}

impl BitSet<u64> {
    pub fn new() -> BitSet<u64> {
        BitSet { bits: 0 }
    }
    pub fn add(&mut self, idx: u8) {
        self.bits |= 1 << idx;
    }
    pub fn contains(&self, idx: u8) -> bool {
        &self.bits & 1 << idx != 0
    }
}

impl FromIterator<u8> for BitSet<u64> {
    fn from_iter<T: IntoIterator<Item=u8>>(iter: T) -> Self {
        let mut bitset = BitSet::new();
        for idx in iter {
            bitset.add(idx);
        }
        return bitset;
    }
}

impl<T> BitAnd<BitSet<T>> for BitSet<T>
    where T: BitAnd<Output=T>
{
    type Output = Self;
    fn bitand(self, rhs: BitSet<T>) -> Self::Output {
        BitSet { bits: self.bits & rhs.bits }
    }
}
