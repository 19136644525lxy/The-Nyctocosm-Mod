#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score @s PUUID0 run data get entity @s data.UUID[0]
execute store result score @s PUUID1 run data get entity @s data.UUID[1]
execute store result score @s PUUID2 run data get entity @s data.UUID[2]
execute store result score @s PUUID3 run data get entity @s data.UUID[3]
tp @s ~ ~ ~ ~ ~
execute rotated ~ 0 run tp @s ^ ^ ^3
execute at @s run function effects:elements/nightmare/transmutate/play
scoreboard players operation 0 temp = @s m.spell.power
scoreboard players operation 0 temp *= #2 const
scoreboard players operation 0 temp += Abhorrence s.duration
scoreboard players operation @s spelltimer = 0 temp
scoreboard players operation 0 temp = @s m.spell.power
scoreboard players operation 0 temp *= #2 const
scoreboard players operation 0 temp += Abhorrence s.damage
scoreboard players operation @s s.damage = 0 temp
tag @s remove justsummoned
execute at @s run playsound nyctocosm:spells.transmutate.abhorrence master @a[distance=..50] ~ ~ ~ 10