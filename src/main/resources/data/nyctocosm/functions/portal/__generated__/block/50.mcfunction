#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=!z] run function nyctocosm:portal/__generated__/block/51
execute as @s[tag=z] run function nyctocosm:portal/__generated__/block/52
scoreboard players remove @s soundtimer 1
execute as @s unless score @s soundtimer matches 1.. run function nyctocosm:portal/__generated__/block/53
execute if score @s swirltimer matches ..0 run function nyctocosm:portal/__generated__/block/54
scoreboard players remove @s swirltimer 1