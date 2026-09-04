#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove justsummoned
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s floatmax = temp0 rng
scoreboard players operation @s floatmax /= #100000 rng
scoreboard players operation @s floatmax %= #20 rng
scoreboard players add @s floatmax 70
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation Rot0 value = temp0 rng
scoreboard players operation Rot0 value /= #100000 rng
scoreboard players operation Rot0 value %= #360 rng
scoreboard players add Rot0 value 0
scoreboard players set 180 value 180
scoreboard players operation Rot0 value -= 180 value
execute store result entity @s Rotation[0] float 1 run scoreboard players get Rot0 value
scoreboard players reset Rot0 value
scoreboard players reset 180 value
execute at @s unless block ~ ~ ~ air run kill @s
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation OffsetX value = temp0 rng
scoreboard players operation OffsetX value /= #100000 rng
scoreboard players operation OffsetX value %= #150 rng
scoreboard players add OffsetX value 0
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation OffsetY value = temp0 rng
scoreboard players operation OffsetY value /= #100000 rng
scoreboard players operation OffsetY value %= #150 rng
scoreboard players add OffsetY value 0
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation OffsetZ value = temp0 rng
scoreboard players operation OffsetZ value /= #100000 rng
scoreboard players operation OffsetZ value %= #150 rng
scoreboard players add OffsetZ value 0
scoreboard players set 2 value 2
scoreboard players operation OffsetX value *= 2 value
scoreboard players operation OffsetY value *= 2 value
scoreboard players operation OffsetZ value *= 2 value
scoreboard players set RNGOffset value 150
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