import csv
import subprocess
import numpy as np # type: ignore


def run_java(jar: str, arg: str, input: str)->str:
    p = subprocess.Popen(['java','-jar',jar, arg], 
        stdin=subprocess.PIPE, 
        stdout=subprocess.PIPE)
    (output,_) = p.communicate(input.encode('utf-8'))
    return output.decode('utf-8')

def createRandomInput()->list[tuple[int]]:
    randomSeed = 5948
    listOfInputs = list()
    rng = np.random.default_rng(68473)
    for i in range(1000):
        randomTuple = (rng.integers(0,569586))
        while randomTuple[0] == randomTuple[1]:
            randomTuple = (rng.integers(0,569586))
        listOfInputs.append(randomTuple)
    return listOfInputs

if __name__ == '__main__':
    