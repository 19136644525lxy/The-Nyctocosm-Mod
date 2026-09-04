#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s ns.shardhailtimer = temp0 rng
scoreboard players operation @s ns.shardhailtimer /= #100000 rng
scoreboard players operation @s ns.shardhailtimer %= #40 rng
scoreboard players add @s ns.shardhailtimer 0
tag @s remove justsummoned