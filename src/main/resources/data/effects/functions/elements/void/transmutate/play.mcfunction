#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["void","transmutate","effect","justsummoned"]}
execute as @e[tag=void,tag=justsummoned] run function effects:elements/void/transmutate/__generated__/block/599
particle flash ~ ~ ~ 0 0 0 0 1
execute positioned ^0.5 ^0 ^1 run function effects:void_vein/singlethin
execute positioned ^0.25 ^0.43 ^1 run function effects:void_vein/singlethin
execute positioned ^-0.25 ^0.43 ^1 run function effects:void_vein/singlethin
execute positioned ^-0.5 ^0 ^1 run function effects:void_vein/singlethin
execute positioned ^-0.25 ^-0.43 ^1 run function effects:void_vein/singlethin
execute positioned ^0.25 ^-0.43 ^1 run function effects:void_vein/singlethin