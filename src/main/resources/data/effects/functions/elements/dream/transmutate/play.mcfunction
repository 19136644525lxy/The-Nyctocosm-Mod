#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["dream","transmutate","effect","justsummoned"]}
execute as @e[tag=dream,tag=justsummoned] run function effects:elements/dream/transmutate/__generated__/block/92
particle flash ~ ~ ~ 0 0 0 0 1
particle firework ~ ~ ~ 0.2 0.2 0.2 0.5 10