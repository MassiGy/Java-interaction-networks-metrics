set terminal png
set title "All Infection Simullations"
set xlabel 'days'
set ylabel 'infected individuals count'
set output 'agregated_infection_simulations.png'

plot 'simulation_scenario1_question5_random.dat' with lines, 'simulation_scenario3_question5_random.dat' with lines, 'simulation_scenario2_question5_random.dat' with lines
