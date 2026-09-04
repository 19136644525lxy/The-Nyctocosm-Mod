#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add spell
tag @s add projectile
tag @s add doomfireball
tp @s ~ ~ ~ ~ ~
data modify entity @s data.UUID set from entity @e[tag=uuidget,limit=1] UUID
execute store result score @s PUUID0 run data get entity @s data.UUID[0]
execute store result score @s PUUID1 run data get entity @s data.UUID[1]
execute store result score @s PUUID2 run data get entity @s data.UUID[2]
execute store result score @s PUUID3 run data get entity @s data.UUID[3]