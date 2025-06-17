set terminal png
set title "Scenario/Simulation 2 in all network configurations"
set xlabel 'days'
set ylabel 'infected individuals count'
set output 'simulation2_in_all_network_config.png'

plot 'simulation_scenario2.dat' with lines title 'initial', 'simulation_scenario2_random.dat' with lines title 'random', 'simulation_scenario2_preferential.dat' with lines title 'preferential'
