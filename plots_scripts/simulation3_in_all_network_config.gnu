set terminal png
set title "Scenario/Simulation 3 in all network configurations"
set xlabel 'days'
set ylabel 'infected individuals count'
set output 'simulation3_in_all_network_config.png'

plot 'simulation_scenario3.dat' with lines title 'initial', 'simulation_scenario3_random.dat' with lines title 'random', 'simulation_scenario3_preferential.dat' with lines title 'preferential'
