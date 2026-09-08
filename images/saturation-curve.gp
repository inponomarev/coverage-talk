set terminal svg size 900,900 dynamic enhanced font "Arial,24"
set output "saturation-curve.svg"

set xlabel "path coverage" font ",36"
set ylabel "code coverage" font ",36"

set xrange [0:1]
set yrange [0:1]

set xtics 0,0.1,1
set ytics 0,0.25,1

set grid
set key off

# Slightly bolder horizontal line at y = 0.75
set arrow 1 from 0,0.75 to 1,0.75 nohead \
    linecolor rgb "#666666" linewidth 2

s = sqrt(10.0)
f(x) = ((3.0*s - 3.0)*sqrt(x)) / (1.0 + (3.0*s - 4.0)*sqrt(x))

sx = 0.1
sy = f(sx)

set label 1 "saturation point" at sx, sy offset 1.2,0.5 font ",28"

plot f(x) with lines \
        linecolor rgb "red" linewidth 6, \
     '+' using (sx):(sy) with points \
        linecolor rgb "red" pointtype 7 pointsize 2

unset output