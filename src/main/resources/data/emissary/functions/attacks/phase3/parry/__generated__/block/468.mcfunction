#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ~ ~ ~ ~ ~
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation Rot1 value = temp0 rng
scoreboard players operation Rot1 value /= #100000 rng
scoreboard players operation Rot1 value %= #180 rng
scoreboard players add Rot1 value 0
scoreboard players remove Rot1 value 90
execute store result entity @s Rotation[1] float 1 run scoreboard players get Rot1 value
scoreboard players reset Rot1 value
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation Rot0 value = temp0 rng
scoreboard players operation Rot0 value /= #100000 rng
scoreboard players operation Rot0 value %= #360 rng
scoreboard players add Rot0 value 0
scoreboard players remove Rot0 value 180
execute store result entity @s Rotation[0] float 1 run scoreboard players get Rot0 value
scoreboard players reset Rot0 value
execute at @s run particle flame ^ ^ ^ ^ ^ ^1000000 0.000001 0
execute at @s run particle flame ^ ^ ^0.2 ^ ^ ^1000000 0.000001 0
execute at @s run particle flame ^ ^ ^0.4 ^ ^ ^1000000 0.000001 0
execute at @s run particle flame ^ ^ ^0.6 ^ ^ ^1000000 0.000001 0
execute at @s run particle flame ^ ^ ^0.8 ^ ^ ^1000000 0.000001 0
execute at @s run particle flame ^ ^ ^1 ^ ^ ^1000000 0.000001 0
execute at @s run particle large_smoke ^ ^ ^ ^ ^ ^-1000000 0.00000015 0
execute at @s run particle large_smoke ^ ^ ^-0.2 ^ ^ ^-1000000 0.00000015 0
execute at @s run particle large_smoke ^ ^ ^-0.4 ^ ^ ^-1000000 0.00000015 0
kill @s