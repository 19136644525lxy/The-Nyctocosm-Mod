#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ~ ~ ~ ~ ~
execute store result score @s value run data get entity @s Rotation[1] 10
scoreboard players operation 0 temp = @s value
scoreboard players add 0 temp 900
scoreboard players operation @s value = 0 temp
execute store result entity @s Pose.Head.[0] float 0.1 run scoreboard players get @s value
scoreboard players operation @s s.circle_timer = TEMP s.circle_timer
tag @s remove justsummoned