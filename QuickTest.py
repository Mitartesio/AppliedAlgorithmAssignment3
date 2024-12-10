import csv
import pandas as pd


data = pd.read_csv("ResultsTest.csv")
df = pd.DataFrame(data)
avg_djikstra = df[df["method"]=="Dijkstra"][["time"]].mean()
print(f"average time for Djikstra: {avg_djikstra}")
avg_Bi_Djikstra = df[df["method"]=="BiDijkstra"][["time"]].mean()
print(f"average time for biDjikstra: {avg_Bi_Djikstra}")


avg_djikstra = df[df["method"]=="Dijkstra"][["relaxations"]].mean()
print(f"average relaxations for Dijkstra: {avg_djikstra}")
avg_Bi_Djikstra = df[df["method"]=="BiDijkstra"][["relaxations"]].mean()
print(f"average relaxations for biDijkstra: {avg_Bi_Djikstra}")
    