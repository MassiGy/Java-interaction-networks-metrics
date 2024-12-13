set terminal png
set title "All Infection Simullations"
set xlabel 'days'
set ylabel 'infected individuals count'
set output 'agregated_infection_simulations_preferential.png'

plot 'simulation_scenario1_preferential.dat' with lines, 'simulation_scenario2_preferential.dat' with lines, 'simulation_scenario3_preferential.dat' with lines
