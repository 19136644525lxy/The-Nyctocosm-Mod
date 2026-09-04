#built using mc-build (https://github.com/mc-build/mc-build)

execute if score Emissary em.phase matches 1 run function emissary:attacks/phase1/choose
execute if score Emissary em.phase matches 2 run function emissary:attacks/phase2/choose
execute if score Emissary em.phase matches 3 run function emissary:attacks/phase3/choose
execute if score Emissary em.phase matches 4 as @e[tag=emissary,tag=emissarymodel] run function emissary:attacks/phase4/choose