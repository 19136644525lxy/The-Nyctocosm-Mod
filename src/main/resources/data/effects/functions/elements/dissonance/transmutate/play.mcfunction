#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["dissonance","transmutate","effect","justsummoned"]}
execute as @e[tag=dissonance,tag=justsummoned] run function effects:elements/dissonance/transmutate/__generated__/block/600
particle flash ~ ~ ~ 0 0 0 0 1
particle explosion ~ ~ ~ 0 0 0 0 1
particle lava ~ ~ ~ 0 0 0 0.2 10
particle falling_lava ~ ~ ~ 0.6 0.6 0.6 0 20
execute summon marker run function effects:elements/dissonance/transmutate/__generated__/block/601
execute summon marker run function effects:elements/dissonance/transmutate/__generated__/block/602
execute summon marker run function effects:elements/dissonance/transmutate/__generated__/block/603
execute summon marker run function effects:elements/dissonance/transmutate/__generated__/block/604
execute summon marker run function effects:elements/dissonance/transmutate/__generated__/block/605
execute summon marker run function effects:elements/dissonance/transmutate/__generated__/block/606