set terminal png
set title "Distance distribution"
set xlabel 'd'
set ylabel 'p(d)'
set output 'distancesDistribution.random.png'

plot 'distancesHistogram.random.dat' with lines title 'distances histogram to distance distribution'

