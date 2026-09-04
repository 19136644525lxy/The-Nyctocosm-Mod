#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=emissary,tag=emissarymodel,limit=1] run function emissary:ai/phase/4/flytarget
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation FlyTimer em.value = temp0 rng
scoreboard players operation FlyTimer em.value /= #100000 rng
scoreboard players operation FlyTimer em.value %= #40 rng
scoreboard players add FlyTimer em.value 20