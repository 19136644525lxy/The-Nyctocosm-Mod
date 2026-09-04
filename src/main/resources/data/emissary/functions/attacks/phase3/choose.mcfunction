#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation AttackTimer em.value = temp0 rng
scoreboard players operation AttackTimer em.value /= #100000 rng
scoreboard players operation AttackTimer em.value %= #15 rng
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
execute at @e[tag=emissary,tag=emissarymodel,limit=1] run function emissary:attacks/phase3/__generated__/block/462