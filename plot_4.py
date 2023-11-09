import numpy as np
import re
import matplotlib.pyplot as plt
from scipy.stats import pearsonr, spearmanr

x = []
y = []
file_content = None
try:
    with open("data_1.txt", "r") as file:
        file_content = file.read()
except FileNotFoundError:
    print(f"The file '{file_path}' was not found.")
except Exception as e:
    print(f"An error occurred: {str(e)}")
for input_string in file_content.splitlines():
    integers = re.findall(r'\d+', input_string)
    int_var1 = int(integers[0])
    elapsed_time_match = int(integers[1])
    bool_var = False
    if "yes" in input_string:
        bool_var = True
    sequence_match = re.search(r'Sequence: ([0-9, ]+)', input_string)

    if sequence_match:
        sequence_string = sequence_match.group(1)
        sequence_values = [int(val) for val in sequence_string.split(', ') if val.isdigit()]
    else:
        sequence_values = []

    if (bool_var == True and int_var1 in sequence_values) or\
       (bool_var == False and int_var1 not in sequence_values):
        y.append(elapsed_time_match/1000)
        x.append(len(sequence_values))

x = np.array(x)
y = np.array(y)
pearson_corr, _ = pearsonr(x, y)
print(f"Pearson's correlation coefficient: {pearson_corr:.4f}")
