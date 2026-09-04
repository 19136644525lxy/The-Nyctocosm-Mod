#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset @s floattimer
execute as @s[tag=float_up] run function nyctocosm:mobs/nightmare_apparition/__generated__/block/17
tag @s[tag=!switch] add float_up
tag @s remove switch