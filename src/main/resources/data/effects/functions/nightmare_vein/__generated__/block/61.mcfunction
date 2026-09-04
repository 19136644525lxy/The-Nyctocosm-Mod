#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s e.veintwist = temp0 rng
scoreboard players operation @s e.veintwist /= #100000 rng
scoreboard players operation @s e.veintwist %= #4 rng
scoreboard players add @s e.veintwist 3
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation Rot1Offset value = temp0 rng
scoreboard players operation Rot1Offset value /= #100000 rng
scoreboard players operation Rot1Offset value %= #60 rng
scoreboard players add Rot1Offset value 0
scoreboard players remove Rot1Offset value 30
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
scoreboard players operation Rot0Offset value %= #60 rng
scoreboard players add Rot0Offset value 0
scoreboard players remove Rot0Offset value 30
execute store result score Rot0 value run data get entity @s Rotation[0]
scoreboard players operation Rot0 value += Rot0Offset value
execute store result entity @s Rotation[0] float 1 run scoreboard players get Rot0 value
scoreboard players reset Rot0Offset value
scoreboard players reset Rot0 value