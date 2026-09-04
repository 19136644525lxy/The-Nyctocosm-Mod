#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s value 1
execute if block ~ ~ ~ minecraft:reinforced_deepslate run function nyctocosm:portal/scan/__generated__/block/116
execute as @s[scores={value=21..}] run tag @s add finishZ
execute as @s[tag=!finishZ] positioned ~-1 ~ ~ run function nyctocosm:portal/scan/east
execute as @s[tag=finishZ] run function nyctocosm:portal/scan/__generated__/block/117