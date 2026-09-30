set terminal svg size 900,900 dynamic enhanced font "Arial,24"
set output "/home/runner/work/coverage-talk/coverage-talk/target/generated-slides/images/coverage-plot-05.svg"

set xlabel "full coverage, %" font ",36"
set ylabel "code coverage, %" font ",36"

set xrange [0:5.12]
set yrange [0:45]

set grid
set key off

$Data << EOD
0 0
0.64 8
1.28 16
2.56 29
5.12 45
EOD

plot $Data using 1:2 with linespoints \
    linecolor rgb "red" linewidth 6 \
    pointtype 7 pointsize 2
