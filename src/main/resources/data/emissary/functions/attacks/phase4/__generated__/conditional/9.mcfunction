#built using mc-build (https://github.com/mc-build/mc-build)

execute if score AttackChoice em.value matches 0..50 run function emissary:attacks/phase4/flying/start
execute if score AttackChoice em.value matches 51..65 run function emissary:attacks/phase4/godrays/start
execute if score AttackChoice em.value matches 66..85 run function emissary:attacks/phase4/fireball/start
execute if score AttackChoice em.value matches 86..100 run function emissary:attacks/phase4/divine_blast/start