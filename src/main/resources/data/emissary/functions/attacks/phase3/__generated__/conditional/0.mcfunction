#built using mc-build (https://github.com/mc-build/mc-build)

execute if score AttackChoice em.value matches 0..10 run function emissary:attacks/phase3/straferight/start
execute if score AttackChoice em.value matches 11..20 run function emissary:attacks/phase3/strafeleft/start
execute if score AttackChoice em.value matches 21..37 run function emissary:attacks/phase3/swordswing/start
execute if score AttackChoice em.value matches 38..46 run function emissary:attacks/phase3/cross/start
execute if score AttackChoice em.value matches 47..54 run function emissary:attacks/phase3/frontkick/start
execute if score AttackChoice em.value matches 55..72 run function emissary:attacks/phase3/swordspin/start
execute if score AttackChoice em.value matches 73..89 run function emissary:attacks/phase3/ground_slam/start
execute if score AttackChoice em.value matches 90..100 run function emissary:attacks/phase3/flipfar/start
scoreboard players set #execute LANG_MC_INTERNAL 1