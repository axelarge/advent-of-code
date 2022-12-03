use std::collections::HashSet;

use crate::timed;


pub fn run(input: String) {
    timed("part 1", || println!("{}", part1(&input)));
    timed("part 2", || println!("{}", part2(&input)));
}

fn part1(input: &String) -> u32 {
    input.lines()
        .map(|line| {
            let l = line.len() / 2;
            line[0..l].bytes().find(|c| line[l..].bytes().any(|lc| lc == *c)).unwrap()
        })
        .map(score)
        .sum()
}

fn part2(input: &String) -> u32 {
    let mut map1 = HashSet::<u8>::new();
    let mut map2 = HashSet::<u8>::new();
    input.lines().array_chunks::<3>()
        .map(|chunk| {
            map1.clear();
            map2.clear();
            map1.extend(chunk[0].bytes());
            map2.extend(chunk[1].bytes().filter(|ch| map1.contains(ch)));
            chunk[2].bytes().find(|ch| map2.contains(ch)).unwrap()
        })
        .map(score)
        .sum()
}

// fn part2(input: &String) -> u32 {
//     input.lines().array_chunks::<3>()
//         .map(|chunk| {
//             let a: HashSet<_> = chunk[0].bytes().collect();
//             let b: HashSet<_> = chunk[1].bytes().filter(|ch| a.contains(ch)).collect();
//             chunk[2].bytes().find(|c| b.contains(c)).unwrap()
//         })
//         .map(score)
//         .sum()
// }

fn score(c: u8) -> u32 {
    (match c {
        b'a'..=b'z' => c - b'a' + 1,
        b'A'..=b'Z' => c - b'A' + 27,
        _ => panic!("Unexpected char {}", c)
    }).into()
}
