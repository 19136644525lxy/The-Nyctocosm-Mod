#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score RotX e.value run data get entity @s Rotation[1] 10
execute as @s[tag=flipped] at @s run function effects:soul_swirl/__generated__/block/633
execute as @s[tag=!flipped,tag=!fliptoggle] at @s run function effects:soul_swirl/__generated__/block/635
tag @s remove fliptoggle
execute store result entity @s Rotation[1] float 0.1 run scoreboard players get RotX e.value
scoreboard players reset RotX e.value
execute at @s run function effects:soul_swirl/raycast
tag @s remove finished
tag @s remove emitted