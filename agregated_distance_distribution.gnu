set terminal png
set title "All Distance distributions put together (Original Graph, Random graph, Preferentiel Graph)"
set xlabel 'd'
set ylabel 'p(d)'
set output 'agregated_distancesDistribution.png'

plot 'distancesHistogram.dat' with lines, 'distancesHistogram.random.dat' with lines, 'distancesHistogram.preferentiel.dat' with lines


