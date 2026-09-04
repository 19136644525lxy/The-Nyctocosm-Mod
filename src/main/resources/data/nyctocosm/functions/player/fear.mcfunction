#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s[predicate=!nyctocosm:in_light,tag=!fear] darknessmeter 1
execute as @s[scores={darknessmeter=200..}] at @s run function nyctocosm:player/__generated__/block/3
execute as @s[predicate=nyctocosm:in_light] run function nyctocosm:player/__generated__/block/4
execute as @s[tag=fear] at @s run function nyctocosm:player/__generated__/block/5