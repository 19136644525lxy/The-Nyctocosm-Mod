#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation AttackChoice em.value = temp0 rng
scoreboard players operation AttackChoice em.value /= #100000 rng
scoreboard players operation AttackChoice em.value %= #100 rng
scoreboard players add AttackChoice em.value 0
execute if score AttackChoice em.value matches 0..45 run function emissary:attacks/phase1/lightning_javelin/start
execute if score AttackChoice em.value matches 46..60 run function emissary:attacks/phase1/pillars/start
execute if score AttackChoice em.value matches 61..75 run function emissary:attacks/phase1/meteor_shower/start
execute if score AttackChoice em.value matches 76..100 run function emissary:attacks/phase1/wingbeat/start
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation AttackTimer em.value = temp0 rng
scoreboard players operation AttackTimer em.value /= #100000 rng
scoreboard players operation AttackTimer em.value %= #80 rng
scoreboard players add AttackTimer em.value 20