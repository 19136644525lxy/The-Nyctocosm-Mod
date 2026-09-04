#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation OffsetX value = temp0 rng
scoreboard players operation OffsetX value /= #100000 rng
scoreboard players operation OffsetX value %= #2000 rng
scoreboard players add OffsetX value 0
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation OffsetY value = temp0 rng
scoreboard players operation OffsetY value /= #100000 rng
scoreboard players operation OffsetY value %= #2000 rng
scoreboard players add OffsetY value 0
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation OffsetZ value = temp0 rng
scoreboard players operation OffsetZ value /= #100000 rng
scoreboard players operation OffsetZ value %= #2000 rng
scoreboard players add OffsetZ value 0
scoreboard players set 2 value 2
scoreboard players operation OffsetX value *= 2 value
scoreboard players operation OffsetY value *= 2 value
scoreboard players operation OffsetZ value *= 2 value
scoreboard players set RNGOffset value 2000
scoreboard players operation OffsetX value -= RNGOffset value
scoreboard players operation OffsetY value -= RNGOffset value
scoreboard players operation OffsetZ value -= RNGOffset value
scoreboard players reset RNGOffset value
execute store result score PosX value run data get entity @s Pos[0] 100
execute store result score PosY value run data get entity @s Pos[1] 100
execute store result score PosZ value run data get entity @s Pos[2] 100
scoreboard players operation PosX value += OffsetX value
scoreboard players operation PosY value += OffsetY value
scoreboard players operation PosZ value += OffsetZ value
execute store result entity @s Pos[0] double 0.01 run scoreboard players get PosX value
execute store result entity @s Pos[1] double 0.01 run scoreboard players get PosY value
execute store result entity @s Pos[2] double 0.01 run scoreboard players get PosZ value
scoreboard players reset OffsetX value
scoreboard players reset OffsetY value
scoreboard players reset OffsetZ value
scoreboard players reset PosX value
scoreboard players reset PosY value
scoreboard players reset PosZ value
scoreboard players reset RNGOffset value
scoreboard players reset 2 value
execute at @s facing entity @a[distance=..300,sort=nearest,limit=1,gamemode=!spectator,tag=!emissarydebug] feet run tp @s ~ ~ ~ ~ ~-10
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation Rot1Offset value = temp0 rng
scoreboard players operation Rot1Offset value /= #100000 rng
scoreboard players operation Rot1Offset value %= #30 rng
scoreboard players add Rot1Offset value 0
scoreboard players remove Rot1Offset value 15
execute store result score Rot1 value run data get entity @s Rotation[1]
scoreboard players operation Rot1 value += Rot1Offset value
execute store result entity @s Rotation[1] float 1 run scoreboard players get Rot1 value
scoreboard players reset Rot1Offset value
scoreboard players reset Rot1 value
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation Rot0Offset value = temp0 rng
scoreboard players operation Rot0Offset value /= #100000 rng
scoreboard players operation Rot0Offset value %= #30 rng
scoreboard players add Rot0Offset value 0
scoreboard players remove Rot0Offset value 15
execute store result score Rot0 value run data get entity @s Rotation[0]
scoreboard players operation Rot0 value += Rot0Offset value
execute store result entity @s Rotation[0] float 1 run scoreboard players get Rot0 value
scoreboard players reset Rot0Offset value
scoreboard players reset Rot0 value
tag @s add emissary
tag @s add attack
tag @s add meteor_shower
tag @s add projectile
tag @s add spell
execute at @s run playsound nyctocosm:emissary.attack.meteor_shower.shoot master @a[distance=..300] ~ ~ ~ 50
execute at @s run particle explosion ~ ~ ~ 1 1 1 0 5 force
execute at @s run particle flash ~ ~ ~ 1 1 1 0 5 force
execute at @s rotated as @e[tag=attack,tag=emissary,tag=shooter,tag=meteor_shower] rotated ~ 90 run function effects:shockwave/meteor_shower_small