import csv
import pandas as pd


data = pd.read_csv("TestResults.csv")
df = pd.DataFrame(data)
avg_djikstra = df[df["method"]=="Djikstra"][["relaxations", "time"]].mean()
print(avg_djikstra)
avg_Bi_Djikstra = df[df["method"]=="DjikstraBirectional"][["relaxations", "time"]].mean()
print(avg_Bi_Djikstra)
    