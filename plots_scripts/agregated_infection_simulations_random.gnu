set terminal png
set title "All Infection Simullations"
set xlabel 'days'
set ylabel 'infected individuals count'
set output 'agregated_infection_simulations_random.png'

plot 'simulation_scenario1_random.dat' with lines, 'simulation_scenario2_random.dat' with lines, 'simulation_scenario3_random.dat' with lines
