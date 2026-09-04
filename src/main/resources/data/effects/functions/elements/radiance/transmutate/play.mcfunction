#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["radiance","transmutate","effect","justsummoned"]}
execute as @e[tag=radiance,tag=justsummoned] run function effects:elements/radiance/transmutate/__generated__/block/593
particle flash ~ ~ ~ 0 0 0 0 1
particle firework ~ ~ ~ 0.2 0.2 0.2 0.5 10