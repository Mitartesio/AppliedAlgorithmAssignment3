import matplotlib.pyplot as plt
import pandas as pd

# test setup to ensure it is working
# plt.plot([1, 2, 3], [4, 5, 6])
# plt.title("Test Plot")
# plt.savefig("test_plot.png")  # Save to a file to test non-GUI usage
# plt.show()


def generate_plot(csv_file: str, title: str, x_label: str, y_label: str, firstGroup: str, sndGroup: str):

    data = pd.read_csv(csv_file)
    grouped = data.groupby("method")

    plt.figure(figsize=(10, 6))
    for algorithm, group in grouped:
        plt.plot(group[firstGroup], group[sndGroup], label=algorithm)

    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.savefig(f"{title}.png")
    # plt.show()

def plot_mean_relaxations(csv_file: str, title: str, x_label: str, y_label: str):
    # Read the CSV file
    data = pd.read_csv(csv_file)

    # Ensure columns have no trailing spaces
    data.columns = data.columns.str.strip()

    # Group by the distance and calculate the mean of relaxations
    mean_relaxations = data.groupby("distance")["relaxations"].mean()

    # Reset the index to prepare for plotting
    mean_relaxations = mean_relaxations.reset_index()

    # Plot the mean number of relaxations vs distance
    plt.figure(figsize=(10, 6))
    plt.plot(mean_relaxations["distance"], mean_relaxations["relaxations"])

    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.savefig(f"{title}.png")  # Save the plot as a PNG file
    plt.show()

# method,relaxations,time,distance

if __name__ == "__main__":
    # generate_plot(
    #     "ResultsTest.csv",
    #     "Time_Distance",
    #     "Distance",
    #     "Time (seconds)",
    #     "relaxations",
    #     "time",
    # )

    # generate_plot(
    #     "ResultsTest.csv",
    #     "Time_Relaxations",
    #     "Number_of_Relaxations",
    #     "Time (seconds)",
    #     "time",
    #     "relaxations",
    # )

    # generate_plot(
    #     "ResultsTest.csv",
    #     "Distance_Relaxations",
    #     "Distance",
    #     "Number_of_Relaxations",
    #     "distance",
    #     "relaxations",
    # )

    plot_mean_relaxations(
        "ResultsTest.csv",
        "Mean Relaxations_Distance",
        "Distance",
        "Number of Relaxations",
    )
