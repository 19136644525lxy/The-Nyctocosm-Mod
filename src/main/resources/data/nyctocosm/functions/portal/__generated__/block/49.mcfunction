#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s in nyctocosm:nyctocosm run tp @s ~ ~ ~
execute at @s run spreadplayers ~ ~ 0 1 false @s
scoreboard players reset @s portaltimer
advancement grant @s only nyctocosm:root