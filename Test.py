import csv
import subprocess
import numpy as np # type: ignore
from typing import NamedTuple, List, Tuple


def run_java(jar: str, arg: str, input: str)->str:
    p = subprocess.Popen(['java','-jar',jar, arg], 
        stdin=subprocess.PIPE, 
        stdout=subprocess.PIPE)
    (output,_) = p.communicate(input.encode('utf-8'))
    return output.decode('utf-8')

#Create random input
def createRandomInput()->list[tuple[int,int]]:
    randomSeed = 92842
    listOfInputs = list()
    rng = np.random.default_rng(randomSeed)
    for i in range(1000):
        randomTuple = (rng.integers(0,569586),rng.integers(0,569586))
        while randomTuple[0] == randomTuple[1]:
            randomTuple = (rng.integers(0,569586),rng.integers(0,569586))
        listOfInputs.append(randomTuple)
    return listOfInputs

class Triple(NamedTuple):
    algorithm: str
    relaxation: int
    time: float


def benchmark(input: List[Tuple[int,int]], algorithm: str, jar: str)->List[Triple]:
    results = []
    inputStr = " ".join(f"{element[0]} {element[1]}" for element in input)
    for line in run_java(jar, algorithm, inputStr).splitlines():
        algorithm, relaxation, time = line.split()
        results.append(Triple(str(algorithm),int(relaxation),float(time)))
    return results


INSTANCES: List[Tuple[str,str]] = [
    ('Djikstra', 'EfficientRoutePlanningContraction/app/build/libs/app.jar'),
    ('BiDjikstra', 'EfficientRoutePlanningContraction/app/build/libs/app.jar')
]

if __name__ == '__main__':
    with open('TestResults.csv','w') as f:
        writer = csv.DictWriter(f,fieldnames=["method", "relaxations", "time"])
        writer.writeheader()
        randomInput = createRandomInput()
        for test, jar in INSTANCES:
            resultList = benchmark(randomInput, test, jar)
            for result in resultList:
                writer.writerow(
                    {
                        "method": result.algorithm,
                        "relaxations": result.relaxation,
                        "time": result.time
                    }
                )