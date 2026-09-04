#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set DaisScan value 1
execute unless block ~ ~ ~ anvil run scoreboard players set DaisScan value 0
execute unless block ~ ~-1 ~ beacon run scoreboard players set DaisScan value 0
execute unless block ~1 ~-1 ~ quartz_block run scoreboard players set DaisScan value 0
execute unless block ~-1 ~-1 ~ quartz_block run scoreboard players set DaisScan value 0
execute unless block ~ ~-1 ~1 quartz_block run scoreboard players set DaisScan value 0
execute unless block ~ ~-1 ~-1 quartz_block run scoreboard players set DaisScan value 0
execute unless block ~2 ~-1 ~ quartz_stairs[facing=west,half=bottom,shape=straight] run scoreboard players set DaisScan value 0
execute unless block ~-2 ~-1 ~ quartz_stairs[facing=east,half=bottom,shape=straight] run scoreboard players set DaisScan value 0
execute unless block ~ ~-1 ~2 quartz_stairs[facing=north,half=bottom,shape=straight] run scoreboard players set DaisScan value 0
execute unless block ~ ~-1 ~-2 quartz_stairs[facing=south,half=bottom,shape=straight] run scoreboard players set DaisScan value 0
execute unless block ~1 ~-1 ~1 waxed_cut_copper_slab[type=bottom] run scoreboard players set DaisScan value 0
execute unless block ~-1 ~-1 ~1 waxed_cut_copper_slab[type=bottom] run scoreboard players set DaisScan value 0
execute unless block ~1 ~-1 ~-1 waxed_cut_copper_slab[type=bottom] run scoreboard players set DaisScan value 0
execute unless block ~-1 ~-1 ~-1 waxed_cut_copper_slab[type=bottom] run scoreboard players set DaisScan value 0
execute unless block ~3 ~-1 ~ #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~-3 ~-1 ~ #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~ ~-1 ~3 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~ ~-1 ~-3 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~3 ~-1 ~1 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~3 ~-1 ~-1 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~-3 ~-1 ~1 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~-3 ~-1 ~-1 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~1 ~-1 ~3 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~-1 ~-1 ~3 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~1 ~-1 ~-3 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~-1 ~-1 ~-3 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~2 ~-1 ~2 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~-2 ~-1 ~2 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~2 ~-1 ~-2 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0
execute unless block ~-2 ~-1 ~-2 #minecraft:candles[lit=true] run scoreboard players set DaisScan value 0