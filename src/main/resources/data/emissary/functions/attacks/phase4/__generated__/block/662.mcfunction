#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #execute LANG_MC_INTERNAL 0
execute if score Flying em.value matches 1 run function emissary:attacks/phase4/__generated__/conditional/8
execute if score #execute LANG_MC_INTERNAL matches 0 run function emissary:attacks/phase4/__generated__/conditional/9