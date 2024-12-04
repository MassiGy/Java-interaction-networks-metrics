set terminal png
set title "Distance distribution"
set xlabel 'd'
set ylabel 'p(d)'
set output 'distancesDistribution.png'

plot 'distancesHistogram.dat' with lines title 'distances histogram to distance distribution'

