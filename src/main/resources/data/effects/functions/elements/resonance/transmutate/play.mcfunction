#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["resonance","transmutate","effect","justsummoned"]}
execute as @e[tag=resonance,tag=justsummoned] run function effects:elements/resonance/transmutate/__generated__/block/594
particle sonic_boom ~ ~ ~ 0 0 0 0 1