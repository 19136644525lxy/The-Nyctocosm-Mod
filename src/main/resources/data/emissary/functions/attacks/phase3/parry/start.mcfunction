#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add Poise em.value 1
scoreboard players set #execute LANG_MC_INTERNAL 0
execute if score Poise em.value >= MaxPoise em.value run function emissary:attacks/phase3/parry/__generated__/conditional/4
execute if score #execute LANG_MC_INTERNAL matches 0 run function emissary:attacks/phase3/parry/__generated__/conditional/5
scoreboard players set Attacking em.value 1
scoreboard players set Idle em.value 0