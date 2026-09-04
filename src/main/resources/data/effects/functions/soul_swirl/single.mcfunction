#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add e.swirlsummon
summon marker ^ ^ ^0.01 {Tags:["justsummoned","swirl","origin","delayed"]}
execute as @e[tag=swirl,tag=origin,tag=justsummoned] at @s run function effects:soul_swirl/__generated__/block/630
tag @s remove e.swirlsummon