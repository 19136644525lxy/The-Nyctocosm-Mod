#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score m.temp.UUID0 value run data get entity @s UUID[0]
execute store result score m.temp.UUID1 value run data get entity @s UUID[1]
execute store result score m.temp.UUID2 value run data get entity @s UUID[2]
execute store result score m.temp.UUID3 value run data get entity @s UUID[3]
execute as @e[tag=m.killMarker] run function magic:__generated__/block/10