#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add success
execute unless block ~3 ~ ~ #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~3 ~ ~1 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~3 ~ ~-1 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~-3 ~ ~ #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~-3 ~ ~1 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~-3 ~ ~-1 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~ ~ ~3 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~1 ~ ~3 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~-1 ~ ~3 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~ ~ ~-3 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~1 ~ ~-3 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~-1 ~ ~-3 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~2 ~ ~2 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~-2 ~ ~2 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~2 ~ ~-2 #minecraft:candles[lit=true] run tag @s remove success
execute unless block ~-2 ~ ~-2 #minecraft:candles[lit=true] run tag @s remove success