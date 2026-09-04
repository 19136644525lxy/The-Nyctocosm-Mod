#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s value 1
execute if block ~ ~ ~ minecraft:reinforced_deepslate run function nyctocosm:portal/scan/__generated__/block/114
execute as @s[scores={value=21..}] run tag @s add finishX
execute as @s[tag=!finishX] positioned ~ ~ ~-1 run function nyctocosm:portal/scan/north
execute as @s[tag=finishX] run function nyctocosm:portal/scan/__generated__/block/115