#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add activated
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s em.timer = temp0 rng
scoreboard players operation @s em.timer /= #100000 rng
scoreboard players operation @s em.timer %= #30 rng
scoreboard players add @s em.timer 60
particle flash ~ ~ ~ 0 0 0 0 1 force