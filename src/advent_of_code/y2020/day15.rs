// fn extend(vec: &mut Vec<i32>, n: usize) {
//     if vec.len() < n + 1 {
//         vec.resize(n + 1, -1);
//     }
// }

pub fn run(input: String) {
    let foo: Vec<usize> = input.split(",").map(|s| { s.trim_end().parse().unwrap() }).collect();
    let mut spoken = vec![0u32; 1 << 26];

    for (i, &n) in foo.iter().enumerate() {
        // extend(&mut spoken, n + 1);
        spoken[n] = (i + 1) as u32;
    }
    let mut n = *foo.last().unwrap();
    let mut prev = foo.len();

    for i in (prev + 1)..=30000000 {
        if i == 2020 + 1 { println!("{}", &n); }
        n = i - 1 - prev;
        // extend(&mut spoken, n + 1);
        prev = {
            let prev = spoken[n];
            if prev == 0 { i } else { prev as usize }
        };

        spoken[n] = i as u32;
    }
    println!("{}", &n);
}
