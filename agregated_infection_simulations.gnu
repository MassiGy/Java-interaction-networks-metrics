set terminal png
set title "All Infection Simullations"
set xlabel 'days'
set ylabel 'infected individuals count'
set output 'agregated_infection_simulations.png'

plot 'simulation_scenario1.dat' with lines, 'simulation_scenario2.dat' with lines, 'simulation_scenario3.dat' with lines
