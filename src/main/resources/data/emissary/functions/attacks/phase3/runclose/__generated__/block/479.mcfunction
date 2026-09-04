#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #execute LANG_MC_INTERNAL 0
execute if predicate emissary:combochance run function emissary:attacks/phase3/runclose/__generated__/conditional/6
execute if score #execute LANG_MC_INTERNAL matches 0 run function emissary:attacks/phase3/runclose/__generated__/conditional/7