#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation AttackTimer em.value = temp0 rng
scoreboard players operation AttackTimer em.value /= #100000 rng
scoreboard players operation AttackTimer em.value %= #50 rng
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
execute if score AttackChoice em.value matches 0..20 run function emissary:attacks/phase2/straferight/start
execute if score AttackChoice em.value matches 21..40 run function emissary:attacks/phase2/strafeleft/start
execute if score AttackChoice em.value matches 41..55 run function emissary:attacks/phase2/celestial_smite/start
execute if score AttackChoice em.value matches 56..70 run function emissary:attacks/phase2/sword_plunge/start
execute if score AttackChoice em.value matches 71..85 run function emissary:attacks/phase2/wing_blast/start
execute if score AttackChoice em.value matches 86..100 run function emissary:attacks/phase2/flame_geysers/start