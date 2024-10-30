import csv
import pandas as pd


data = pd.read_csv("TestResults.csv")
df = pd.DataFrame(data)
avg_djikstra = df[df["method"]=="Djikstra"][["time"]].mean()
print(f"average time for Djikstra: {avg_djikstra}")
avg_Bi_Djikstra = df[df["method"]=="DjikstraBirectional"][["time"]].mean()
print(f"average time for biDjikstra: {avg_Bi_Djikstra}")


avg_djikstra = df[df["method"]=="Djikstra"][["relaxations"]].mean()
print(f"average relaxations for Djikstra: {avg_djikstra}")
avg_Bi_Djikstra = df[df["method"]=="DjikstraBirectional"][["relaxations"]].mean()
print(f"average relaxations for biDjikstra: {avg_Bi_Djikstra}")
    