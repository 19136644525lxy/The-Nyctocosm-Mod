#built using mc-build (https://github.com/mc-build/mc-build)

execute if score AttackChoice em.value matches 0..40 run function emissary:attacks/phase3/runclose/start
execute if score AttackChoice em.value matches 41..60 run function emissary:attacks/phase3/flame_slash/start
execute if score AttackChoice em.value matches 61..80 run function emissary:attacks/phase3/seeking_missiles/start
execute if score AttackChoice em.value matches 81..100 run function emissary:attacks/phase3/lightning_field/start