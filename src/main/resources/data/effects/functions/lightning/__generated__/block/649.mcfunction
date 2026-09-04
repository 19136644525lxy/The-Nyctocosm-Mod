#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add Lightning e.zigzagpoint 1
summon marker ~ ~ ~ {Tags:["lightningsegment","justsummoned"]}
execute as @e[tag=lightningsegment,tag=justsummoned] run function effects:lightning/__generated__/block/650