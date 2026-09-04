#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ^-2.5 ^ ^ ~ ~
data merge entity @s {Tags:["emissary","attack","wingbeat","projectile","spell"]}
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s em.value = temp0 rng
scoreboard players operation @s em.value /= #100000 rng
scoreboard players operation @s em.value %= #50 rng
scoreboard players add @s em.value 0