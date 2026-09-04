#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set #execute LANG_MC_INTERNAL 0
execute if predicate emissary:combochance run function emissary:attacks/phase3/__generated__/conditional/2
execute if score #execute LANG_MC_INTERNAL matches 0 run function emissary:attacks/phase3/__generated__/conditional/3