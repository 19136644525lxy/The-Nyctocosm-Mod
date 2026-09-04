#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add attack
tag @s add emissary
tag @s add flame_geysers
tag @s add geyser
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s em.timer = temp0 rng
scoreboard players operation @s em.timer /= #100000 rng
scoreboard players operation @s em.timer %= #130 rng
scoreboard players add @s em.timer 20