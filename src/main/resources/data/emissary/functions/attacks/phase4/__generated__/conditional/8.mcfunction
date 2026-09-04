#built using mc-build (https://github.com/mc-build/mc-build)

execute if score AttackChoice em.value matches 0..50 run function emissary:attacks/phase4/idle/start
execute if score AttackChoice em.value matches 51..65 run function emissary:attacks/phase4/apocalypse_rain/start
execute if score AttackChoice em.value matches 66..85 run function emissary:attacks/phase4/corkscrew/start
execute if score AttackChoice em.value matches 86..100 run function emissary:attacks/phase4/grapple/start
scoreboard players set #execute LANG_MC_INTERNAL 1