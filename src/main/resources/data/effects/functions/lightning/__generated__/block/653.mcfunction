#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation OffsetX value = temp0 rng
scoreboard players operation OffsetX value /= #100000 rng
scoreboard players operation OffsetX value %= #200 rng
scoreboard players add OffsetX value 0
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation OffsetY value = temp0 rng
scoreboard players operation OffsetY value /= #100000 rng
scoreboard players operation OffsetY value %= #200 rng
scoreboard players add OffsetY value 0
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation OffsetZ value = temp0 rng
scoreboard players operation OffsetZ value /= #100000 rng
scoreboard players operation OffsetZ value %= #200 rng
scoreboard players add OffsetZ value 0
scoreboard players set RNGOffset value 100
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