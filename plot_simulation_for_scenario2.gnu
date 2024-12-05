# Gnuplot Script to plot Susceptible and Infected Nodes

# Set the output to a PNG file (optional, can be omitted for interactive plotting)
set terminal pngcairo enhanced
set output 'propagation_plot_for_scenario2.png'

# Set titles and labels
set title "Propagation of Susceptible and Infected Nodes"
set xlabel "Day Number"
set ylabel "Node Count"

# Set the grid for better readability
set grid

# Plot the data as two curves, one for Susceptible and one for Infected
plot 'scenario2_simulation_results.txt' using 1:2 with lines title 'Susceptible Nodes Count', \
     'scenario2_simulation_results.txt' using 1:3 with lines title 'Infected Nodes Count'

# Unset the output if plotting interactively
unset output

