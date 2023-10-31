import tkinter as tk
import random
import time

def generate_random_numbers(min_count, max_count, min_val, max_val):
    n = random.randint(min_count, max_count)
    random_numbers = random.sample(range(min_val, max_val + 1), n)
    return random_numbers
state = 0
random_num = None
was_this_present = None
start_time = None
# Function to update the label text
def update_label_text():
    global state
    global random_num
    global start_time
    global was_this_present
    global question_num
    new_text = None
    if state == 0:
        random_num = [str(item) for item in generate_random_numbers(2, 7, 0, 15)]
        new_text = " ".join(random_num)
        state = 1
    elif state == 1:
        if random.randint(0, 1) == 0:
            question_num = random.choice(random_num)
        else:
            tmp = set([str(i) for i in range(0, 16)])
            tmp = tmp - set(random_num)
            tmp = list(tmp)
            question_num = random.choice(tmp)
        if question_num in random_num:
            was_this_present = True
        else:
            was_this_present = False
        new_text = "was the number " + str(question_num) + " present??"
        state = 2
        start_time = time.time()
    label.config(text=new_text)
def on_y_press(event):
    global was_this_present
    global start_time
    global state
    print(str(was_this_present)+" "+str(len(random_num))+" ", end="", sep="")
    if was_this_present is True:
        print("correct answer ", end="", sep="")
        print(time.time()-start_time)
    else:
        print("wrong answer ", end="", sep="")
        print(time.time()-start_time)
    if state == 2:
        state = 0
        update_label_text()
        
def on_n_press(event):
    global was_this_present
    global start_time
    global state
    print(str(was_this_present)+" "+str(len(random_num))+" ", end="", sep="")
    if was_this_present is False:
        print("correct answer ", end="", sep="")
        print(time.time()-start_time)
    else:
        print("wrong answer ", end="", sep="")
        print(time.time()-start_time)
    if state == 2:
        state = 0
        update_label_text()

def on_x_press(event):
    global state
    state = 0
    update_label_text()
    
# Create the main window
root = tk.Tk()
root.title("Tkinter Application")
root.state('zoomed')
# Create a label widget
label = tk.Label(root, text="ready?", font=("Helvetica", 25))
label.place(relx=0.5, rely=0.5, anchor=tk.CENTER)

# Bind the Enter key to update the label text
root.bind("<Return>", lambda event: update_label_text())
root.bind("y", on_y_press)
root.bind("n", on_n_press)
root.bind("<Escape>", on_x_press)
# Start the main loop
root.mainloop()
