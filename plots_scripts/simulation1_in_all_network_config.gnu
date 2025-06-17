set terminal png
set title "Scenario/Simulation 1 in all network configurations"
set xlabel 'days'
set ylabel 'infected individuals count'
set output 'simulation1_in_all_network_config.png'

plot 'simulation_scenario1.dat' with lines title 'initial', 'simulation_scenario1_random.dat' with lines title 'random', 'simulation_scenario1_preferential.dat' with lines title 'preferential'
