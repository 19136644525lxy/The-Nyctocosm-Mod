#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation AttackTimer em.value = temp0 rng
scoreboard players operation AttackTimer em.value /= #100000 rng
scoreboard players operation AttackTimer em.value %= #30 rng
scoreboard players add AttackTimer em.value 0
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation AttackChoice em.value = temp0 rng
scoreboard players operation AttackChoice em.value /= #100000 rng
scoreboard players operation AttackChoice em.value %= #100 rng
scoreboard players add AttackChoice em.value 0
execute at @s unless entity @a[distance=..60,gamemode=!spectator,tag=!emissarydebug] run function emissary:attacks/phase4/__generated__/block/661
execute as @s[tag=!decided] run function emissary:attacks/phase4/__generated__/block/662
tag @s remove decided