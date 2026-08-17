set terminal svg size 900,900 dynamic enhanced font "Arial,36"
set output "/home/runner/work/coverage-talk/coverage-talk/target/generated-slides/images/coverage-07.svg"

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
set object 4 rect at 0.43,0.44 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 5 rect at 0.84,0.36 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 6 rect at 0.11,0.9 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 7 rect at 0.05,0.42 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 8 rect at 0.7,0.68 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 9 rect at 0.69,0.14 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 10 rect at 0.92,0.64 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 11 rect at 0.71,0.92 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 12 rect at 0.41,0.71 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 13 rect at 0.47,0.27 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 14 rect at 0.24,0.43 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 15 rect at 0.73,0.46 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 16 rect at 0.29,0.13 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 17 rect at 0.41,0.95 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 18 rect at 0.05,0.79 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 19 rect at 0.3,0.95 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 20 rect at 0.3,0.23 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 21 rect at 0.59,0.27 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 22 rect at 0.82,0.51 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 23 rect at 0.8,0.19 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 24 rect at 0.24,0.6 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 25 rect at 0.69,0.56 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 26 rect at 0.88,0.27 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 27 rect at 0.47,0.56 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 28 rect at 0.69,0.82 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 29 rect at 0.23,0.71 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 30 rect at 0.62,0.43 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 31 rect at 0.52,0.46 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 32 rect at 0.29,0.32 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2
set object 33 rect at 0.38,0.13 size 0.08,0.08 fc rgb "#4F81BD" fs solid 0.35 border rgb "#4F81BD" lw 2

# Axes of the main space
set arrow 1 from 0,0 to 1,0 nohead lw 2 lc rgb "#777777"
set arrow 2 from 0,0 to 0,1 nohead lw 2 lc rgb "#777777"

# Projection bar directly under X axis
set object 34 rect from 0,-0.06 to 1,-0.01 fc rgb "#F2B8B5" fs solid 1.0 border rgb "#777777" lw 2

# Green projected intervals
set object 35 rect from 0.01,-0.06 to 0.16,-0.01 fc rgb "#70AD47" fs solid 0.85 border rgb "#70AD47" lw 2
set object 36 rect from 0.19,-0.06 to 0.34,-0.01 fc rgb "#70AD47" fs solid 0.85 border rgb "#70AD47" lw 2
set object 37 rect from 0.34,-0.06 to 0.96,-0.01 fc rgb "#70AD47" fs solid 0.85 border rgb "#70AD47" lw 2

# Labels
set label 1 "Full space of program execution paths" at screen 0.095, screen 0.6 rotate by 90 center font ",36"
set label 2 "code cov. = 92.00%, full space cov. = 20.48%" at 0.5,-0.09 center font ",36"

plot 1/0 notitle
