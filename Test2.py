import csv
import numpy as np  # type: ignore
from typing import List, Tuple


# Function to generate random node pairs
def createRandomInput() -> List[Tuple[int, int]]:
    randomSeed = 92842
    listOfInputs = []
    rng = np.random.default_rng(randomSeed)
    for i in range(1000):
        randomTuple = (rng.integers(0, 569586), rng.integers(0, 569586))
        while (
            randomTuple[0] == randomTuple[1]
        ):  # Ensure start and end nodes are different
            randomTuple = (rng.integers(0, 569586), rng.integers(0, 569586))
        listOfInputs.append(randomTuple)
    return listOfInputs


# Save the generated pairs to a text file
def savePairsToFile(pairs: List[Tuple[int, int]], file_path: str):
    with open(file_path, "w") as f:
        for start, end in pairs:
            f.write(f"{start} {end}\n")


if __name__ == "__main__":
    node_pairs = createRandomInput()
    savePairsToFile(
        node_pairs,
        "EfficientRoutePlanningContraction/app/src/main/resources/node_pairs.txt",
    )
