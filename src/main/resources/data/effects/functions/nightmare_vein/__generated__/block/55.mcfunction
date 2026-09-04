#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ~ ~ ~ ~ ~
tag @s remove justsummoned
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s e.veintwist = temp0 rng
scoreboard players operation @s e.veintwist /= #100000 rng
scoreboard players operation @s e.veintwist %= #5 rng
scoreboard players add @s e.veintwist 5