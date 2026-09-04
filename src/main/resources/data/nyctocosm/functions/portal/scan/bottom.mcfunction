#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s value 1
execute if block ~ ~ ~ minecraft:reinforced_deepslate run function nyctocosm:portal/scan/__generated__/block/112
execute as @s[scores={value=7..}] run tag @s add finishY
execute as @s[tag=!finishY] positioned ~ ~-1 ~ run function nyctocosm:portal/scan/bottom
execute as @s[tag=finishY] run function nyctocosm:portal/scan/__generated__/block/113