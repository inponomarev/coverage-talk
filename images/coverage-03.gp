set terminal svg size 900,900 dynamic enhanced font "Arial,36"
set output "/home/runner/work/coverage-talk/coverage-talk/target/generated-slides/images/coverage-03.svg"

unset key
unset border
unset xtics
unset ytics

set xrange [0:1]
set yrange [-0.12:1.01]
set size ratio -1

set lmargin at screen 0.14
set rmargin at screen 0.98
set bmargin at screen 0.1
set tmargin at screen 0.97

set style fill solid 0.35 noborder

# Main square frame
set object 1 rect from 0,0 to 1,1 fs empty border rgb "#777777" lw 2

# Patches
set object 2 rect at 0.12,0.55 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 3 rect at 0.38,0.32 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2

# Axes of the main space
set arrow 1 from 0,0 to 1,0 nohead lw 2 lc rgb "#777777"
set arrow 2 from 0,0 to 0,1 nohead lw 2 lc rgb "#777777"

# Projection bar directly under X axis
set object 4 rect from 0,-0.06 to 1,-0.01 fc rgb "#F2B8B5" fs solid 1.0 border rgb "#777777" lw 2

# Green projected intervals
set object 5 rect from 0.08,-0.06 to 0.16,-0.01 fc rgb "#70AD47" fs solid 0.85 border rgb "#70AD47" lw 2
set object 6 rect from 0.34,-0.06 to 0.42,-0.01 fc rgb "#70AD47" fs solid 0.85 border rgb "#70AD47" lw 2

# Labels
set label 1 "Full space of program execution paths" at screen 0.095, screen 0.6 rotate by 90 center font ",36"
set label 2 "code cov. = 16.00%, full space cov. = 1.28%" at 0.5,-0.09 center font ",36"

plot 1/0 notitle
