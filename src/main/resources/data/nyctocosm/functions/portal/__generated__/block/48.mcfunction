#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s portaltimer 1
execute if score @s portaltimer matches 150.. run function nyctocosm:portal/__generated__/block/49
effect give @s nausea 5 0 true
effect give @s darkness 5 0 true