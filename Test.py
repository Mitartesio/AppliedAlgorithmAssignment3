import csv
import subprocess
from typing import Dict, List, Tuple
import numpy as np # type: ignore


def run_java(jar: str, arg: str, input: str)->str:
    p = subprocess.Popen(['java','-jar',jar, arg], 
        stdin=subprocess.PIPE, 
        stdout=subprocess.PIPE)
    (output,_) = p.communicate(input.encode('utf-8'))
    return output.decode('utf-8')

#Create random input
def createRandomInput()->list[tuple[int,int]]:
    randomSeed = 5948
    listOfInputs = list()
    rng = np.random.default_rng(randomSeed)
    for i in range(1000):
        randomTuple = (rng.integers(0,569586),rng.integers(0,569586))
        while randomTuple[0] == randomTuple[1]:
            randomTuple = (rng.integers(0,569586),rng.integers(0,569586))
        listOfInputs.append(randomTuple)
    return listOfInputs

def benchmark(input: List[Tuple[int,int]], algorithm, jar)->List[Tuple[int,float]]:
    results = []
    inputStr = " ".join(f"{element[0]} {element[1]}" for element in input)
    for line in run_java(jar, algorithm, inputStr).splitlines():
        relaxation, time = line.split()
        results.append((int(relaxation),float(time)))
    return results

INSTANCES: List[Tuple[str,str]] = [
    ('Test', 'EfficientRoutePlanningContraction/app/build/libs/app.jar')
]

if __name__ == '__main__':
    with open('TestResults.csv','w') as f:
        writer = csv.DictWriter(f,fieldnames=["method", "relaxations", "time"])
        writer.writeheader()
        randomInput = createRandomInput()
        for algorithm, jar in INSTANCES:
            resultList = benchmark(randomInput, algorithm, jar)
            for tuple in resultList:
                writer.writerow(
                    {
                        "method": algorithm,
                        "relaxations": tuple[0],
                        "time": tuple[1]
                    }
                )