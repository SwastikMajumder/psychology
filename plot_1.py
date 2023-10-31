import matplotlib.pyplot as plt
file_path = "psychology.txt"
with open(file_path, 'r') as file:
    file_content = file.read()
file_line = file_content.splitlines()

coordinates = []
to_plot = [0]*8
to_len = [0]*8
for item in file_line:
    item_split = item.split()
    if item_split[2] == "wrong":
        continue
    x = int(item_split[1])
    y = float(item_split[4])
    to_plot[x] += y
    to_len[x] += 1
for i in range(7):
    if to_len[i] == 0:
        continue
    coordinates.append((i, to_plot[i]/to_len[i]))

x_values, y_values = zip(*coordinates)

# Create a basic scatter plot
plt.scatter(x_values, y_values, label='Data Points', color='b', marker='o')

# Add labels and a title
plt.xlabel('number of digits memorized')
plt.ylabel('response time in seconds')
plt.title('Scatter Plot of (x, y) Coordinates')

# Show a legend (if needed)
plt.legend()

# Display the plot
plt.show()
