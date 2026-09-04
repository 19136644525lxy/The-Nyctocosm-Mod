#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score @s value run data get entity @s Pose.Head.[2]
execute if score @s value matches 360.. run scoreboard players set @s value 0
scoreboard players add @s value 1
execute store result entity @s Pose.Head.[2] float 1 run scoreboard players get @s value