# Python script to read in data and create line graphs
# Murray Inglis - INGMUR002

import importlib
import subprocess

# Check if Matplotlib is installed
try:
    importlib.import_module('matplotlib')
except ImportError:
    # Matplotlib is not installed, install it using apt-get
    subprocess.call(['sudo', 'apt-get', 'install', 'python3-matplotlib'])
    importlib.import_module('matplotlib')

import matplotlib.pyplot as plt
import numpy as np

# Read in the file
with open('data/Plot.txt', 'r') as f:
    next(f) # skipping heading
    lines = f.readlines()

# Convert the lines to arrays
V_array = []
ops_array = []
ElogV_array = []

for line in lines:
    values = line.strip().split()
    V_array.append(int(values[0]))
    ops_array.append(int(values[1]))
    ElogV_array.append(float(values[2]))

plt.plot(ops_array, label='ops')
plt.plot(ElogV_array, label='ElogV')
plt.xlabel('Experiment number')
plt.ylabel('Runtime')
plt.legend()
plt.savefig("data/chart_pylib.jpeg")
plt.show()

# Calculating correlation coefficient
V_array = np.array(V_array)
ops_array = np.array(ops_array)
ElogV_array = np.array(ElogV_array)

r = np.corrcoef(ops_array, ElogV_array)[0,1]

print("Correlation coefficient: ", r)

plt.plot(ops_array, ElogV_array, 'o')
plt.title("Correlation between measured operations and theoretical bound")
plt.savefig("data/correlation.jpeg")
plt.show()