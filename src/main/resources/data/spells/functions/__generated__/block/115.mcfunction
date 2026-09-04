#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s spelltimer 1
execute at @s run particle cloud ~ ~1 ~ 0.3 0.6 0.3 0.2 5 force
fill ~-6 ~-6 ~-6 ~6 ~6 ~6 minecraft:air replace #nyctocosm:vacable
execute store result score UUID0 value run scoreboard players get @s PUUID0
execute store result score UUID1 value run scoreboard players get @s PUUID1
execute store result score UUID2 value run scoreboard players get @s PUUID2
execute store result score UUID3 value run scoreboard players get @s PUUID3
execute as @a[distance=..6] run function spells:__generated__/block/116
execute at @s as @e[type=#nyctocosm:manipulable,distance=..6,tag=!matchedid] run function spells:__generated__/block/118
tag @a remove matchedid
execute if score @s spelltimer matches ..0 run function spells:__generated__/block/121