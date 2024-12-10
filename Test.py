import csv
import subprocess
import numpy as np # type: ignore
from typing import NamedTuple, List, Tuple


def run_java(jar: str, arg: str, input: str)->str:
    p = subprocess.Popen(['java',"-Xmx4g",'-jar',jar, arg], 
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




def benchmark(input: List[Tuple[int,int]], algorithm: str, jar: str)->List[Tuple[str,int,float,float]]:
    results = []
    inputStr = " ".join(f"{element[0]} {element[1]}" for element in input)
    for line in run_java(jar, algorithm, inputStr).splitlines():
        relaxation, time, distance = line.split()
        results.append((str(algorithm), int(relaxation), float(time), float(distance)))
    return results

def benchmark1(input1: str, input2: str, algorithm:str, jar:str):
    inputStr = f"{input1} {input2}"
    output = run_java(jar, algorithm, inputStr)
    print(output)


INSTANCES: List[Tuple[str,str]] = [
    ('Dijkstra', 'EfficientRoutePlanningContraction/app/build/libs/app.jar'),
    # ('BiDijkstra', 'EfficientRoutePlanningContraction/app/build/libs/app.jar'),
    # ('QueryDijkstra', 'EfficientRoutePlanningContraction/app/build/libs/app.jar')
]

if __name__ == '__main__':
    with open('ResultsTest.csv','w') as f:
        writer = csv.DictWriter(f,fieldnames=["method", "relaxations", "time", "distance"])
        writer.writeheader()
        randomInput = createRandomInput()
        for test, jar in INSTANCES:
            resultList = benchmark(randomInput, test, jar)
            for result in resultList:
                writer.writerow(
                    {
                        "method": result[0],
                        "relaxations": result[1],
                        "time": result[2],
                        "distance": result[3]
                    }
                )
# if __name__ == '__main__':
#     benchmark1("10","9382","Test","EfficientRoutePlanningContraction/app/build/libs/app.jar")